package com.kidstopia.kidstopia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.kidstopia.kidstopia.model.ChannelItems
import com.kidstopia.kidstopia.model.PopularItems
import com.kidstopia.kidstopia.model.SearchItems
import com.kidstopia.kidstopia.presentation.network.NetworkClient
import com.kidstopia.kidstopia.repository.NetworkRepository
import com.kidstopia.kidstopia.repository.NetworkRepositoryImpl
import kotlinx.coroutines.launch


// 예외 처리에 대한 내용 필요

class HomeViewModel(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _getPopularData: MutableLiveData<MutableList<PopularItems>> = MutableLiveData()
    val getPopularData: LiveData<MutableList<PopularItems>> get() = _getPopularData

    private val _getSearchData: MutableLiveData<MutableList<SearchItems>> = MutableLiveData()
    val getSearchData: LiveData<MutableList<SearchItems>> get() = _getSearchData

    private val _getChannelData: MutableLiveData<MutableList<ChannelItems>> = MutableLiveData()
    val getChannelData: LiveData<MutableList<ChannelItems>> get() = _getChannelData

    fun getPopularData() = viewModelScope.launch {
        val popularData = networkRepository.getPopularVideoList(
            NetworkClient.AUTH_KEY,
            "snippet",
            chart = "mostPopular",
            maxResults = 5
        )
        _getPopularData.value = popularData.items
    }

    fun getSearchData(query: String) = viewModelScope.launch {
        val searchData = networkRepository.getSearchOrderVideoList(query)
        _getSearchData.value = searchData.items

        val channelData = networkRepository.getChannel(
            AUTH_KEY = NetworkClient.AUTH_KEY,
            part = "snippet",
            id = searchData.items.joinToString { it.snippet.channelId }
        )
        _getChannelData.value = channelData.items
    }
}

class HomeViewModelFactory() : ViewModelProvider.Factory {
    private val networkRepository = NetworkRepositoryImpl(
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
        return HomeViewModel(
            networkRepository
        ) as T
    }
}