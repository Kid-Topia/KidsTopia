package com.limheejin.kidstopia.repository


import com.limheejin.kidstopia.model.ChannelData
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.SearchData
import com.limheejin.kidstopia.presentation.network.ChannelInterface
import com.limheejin.kidstopia.presentation.network.PopularVideoInterface
import com.limheejin.kidstopia.presentation.network.SearchInterface
import com.limheejin.kidstopia.presentation.network.VideoInterface

class NetworkRepositoryImpl(
    private val videoInterface : VideoInterface,
    private val channelInterface : ChannelInterface,
    private val searchInterface: SearchInterface,
    private val popularVideoInterface: PopularVideoInterface
): NetworkRepository {
    override suspend fun getPopularVideoList(
        AUTH_KEY: String,
        part: String,
        chart: String,
        maxResults: Int
    ): PopularData {
        return popularVideoInterface.getPopularVideoList(
            AUTH_KEY,
            part,
            chart,
            maxResults
        )
    }

    override suspend fun getVideo(AUTH_KEY: String, part: String, videoId: String): PopularData {
        return videoInterface.getVideoData(
            AUTH_KEY,
            part,
            videoId
        )
    }

    override suspend fun getChannel(AUTH_KEY: String, part: String, id: String): ChannelData {
        return channelInterface.getChannelData(
            AUTH_KEY,
            part,
            id
        )
    }

    override suspend fun getSearchVideoList(query: String): SearchData {
        return searchInterface.getSearchList(query)
    }

}