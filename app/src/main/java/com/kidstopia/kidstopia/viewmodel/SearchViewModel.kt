package com.kidstopia.kidstopia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.kidstopia.kidstopia.model.SearchItems
import com.kidstopia.kidstopia.presentation.network.NetworkClient
import com.kidstopia.kidstopia.repository.NetworkRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchViewModel(private val repository: NetworkRepositoryImpl) : ViewModel() {
    private val _getSearchData: MutableLiveData<MutableList<SearchItems>> = MutableLiveData()
    val getSearchData: LiveData<MutableList<SearchItems>> get() = _getSearchData

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun searchVideos(query: String) {
        viewModelScope.launch {
            try {
                val searchItems = withContext(Dispatchers.IO) {
                    repository.getSearchVideoList(query).items
                }
                _getSearchData.value = searchItems
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun handleException(exception: Exception) {
        val errorMessage = when (exception) {
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
    private val repository = NetworkRepositoryImpl(
        NetworkClient.youtubeApiVideo,
        NetworkClient.youtubeApiChannel,
        NetworkClient.youtubeApiSearch,
        NetworkClient.youtubeApiPopularVideo,
        NetworkClient.youtubeApiOrderSearch
    )

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return SearchViewModel(
            repository
        ) as T
    }
}