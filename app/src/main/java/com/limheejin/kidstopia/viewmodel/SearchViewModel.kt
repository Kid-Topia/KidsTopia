package com.limheejin.kidstopia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.limheejin.kidstopia.model.SearchItems
import com.limheejin.kidstopia.presentation.network.NetworkClient
import com.limheejin.kidstopia.repository.Repository
import com.limheejin.kidstopia.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException


// UI 와 직접적으로 상호작용하는 코드는 SearchFragment, 데이터 처리와 비즈니스 로직은 ViewModel
// LiveData를 통해 데이터 변경을 감지하면 UI 업데이트

class SearchViewModel(private val repository: Repository) : ViewModel() {
    private val _getSearchData: MutableLiveData<MutableList<SearchItems>> = MutableLiveData()
    val getSearchData: LiveData<MutableList<SearchItems>> get() = _getSearchData

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun searchVideos(query: String) {
        viewModelScope.launch {
            try {
                val searchItems = withContext(Dispatchers.IO){
                    repository.getSearchVideoList(query).items
                }
                _getSearchData.value = searchItems
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

//    fun getSearchData(query: String) = viewModelScope.launch {
//        _getSearchData.value = repository.getSearchVideoList(query).items
//    }

    private fun handleException(exception: Exception) {
        val errorMessage = when (exception) {
//            is IOException -> "IO Exception 오류가 발생하였습니다."
            is HttpException -> {
                when (exception.code()) {
                    400 -> "Bad Request 오류 발생"
                    401 -> "Unauthorized 오류 발생"
                    403 -> "403 오류 발생"
                    404 -> "not found 오류 발생"
                    else -> "알 수 없는 오류가 발생하였습니다."
                }
            }

            else -> "알 수 없는 오류가 발생하였습니다."
        }
        _errorMessage.value = errorMessage
    }
}


class SearchVideoViewModelFactory : ViewModelProvider.Factory {
    private val repository = RepositoryImpl(NetworkClient.youtubeApiSearch)
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        return SearchViewModel(
            repository
        ) as T
    }
}