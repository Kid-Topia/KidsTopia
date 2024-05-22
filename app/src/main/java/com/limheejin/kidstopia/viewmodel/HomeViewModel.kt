package com.limheejin.kidstopia.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.limheejin.kidstopia.model.ChannelItems
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.PopularItems
import com.limheejin.kidstopia.model.SearchItems
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.presentation.network.NetworkClient
import com.limheejin.kidstopia.repository.NetworkRepository
import com.limheejin.kidstopia.repository.NetworkRepositoryImpl
import com.limheejin.kidstopia.repository.RoomRepository
import com.limheejin.kidstopia.repository.RoomRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val networkRepository: NetworkRepository
): ViewModel() {

    private val _getPopularData: MutableLiveData<MutableList<PopularItems>> = MutableLiveData()
    val getPopularData: LiveData<MutableList<PopularItems>> get() = _getPopularData
    fun getPopularData() = viewModelScope.launch {
        val popularData = networkRepository.getPopularVideoList(
            NetworkClient.AUTH_KEY,
            "snippet",
            chart = "mostPopular",
            maxResults = 5
        )
        _getPopularData.value = popularData.items
    }

    private val _getSearchData: MutableLiveData<MutableList<SearchItems>> = MutableLiveData()
    val getSearchData: LiveData<MutableList<SearchItems>> get() = _getSearchData
    fun getSearchData(query: String) = viewModelScope.launch {
        val searchData = networkRepository.getSearchOrderVideoList(query)
        _getSearchData.value = searchData.items
    }

    fun getChannelList() = viewModelScope.launch {
        val channelList = _getSearchData.value?.joinToString { it.snippet.channelId }
        if (channelList != null) {
            getChannelData(channelList)
        }
    }

    private val _getChannelData: MutableLiveData<MutableList<ChannelItems>> = MutableLiveData()
    val getChannelData: LiveData<MutableList<ChannelItems>> get() = _getChannelData
    fun getChannelData(channelList: String) = viewModelScope.launch {
        val channelData = networkRepository.getChannel(
            AUTH_KEY = NetworkClient.AUTH_KEY,
            part = "snippet",
            id = channelList
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