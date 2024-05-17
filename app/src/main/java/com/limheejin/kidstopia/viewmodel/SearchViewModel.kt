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
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository): ViewModel() {
    private val _getSearchData: MutableLiveData<MutableList<SearchItems>> = MutableLiveData()
    val getSearchData: LiveData<MutableList<SearchItems>> get() =_getSearchData
    fun getSearchData(query: String) = viewModelScope.launch {
        _getSearchData.value = repository.getSearchVideoList(query).items
    }
}


class PopularVideoViewModelFactory : ViewModelProvider.Factory {
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