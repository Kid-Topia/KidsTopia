package com.kidstopia.kidstopia.repository

import com.kidstopia.kidstopia.model.ChannelData
import com.kidstopia.kidstopia.model.PopularData
import com.kidstopia.kidstopia.model.SearchData
import com.kidstopia.kidstopia.presentation.network.ChannelInterface
import com.kidstopia.kidstopia.presentation.network.PopularVideoInterface
import com.kidstopia.kidstopia.presentation.network.SearchInterface
import com.kidstopia.kidstopia.presentation.network.SearchOrderInterface
import com.kidstopia.kidstopia.presentation.network.VideoInterface

class NetworkRepositoryImpl(
    private val videoInterface: VideoInterface,
    private val channelInterface: ChannelInterface,
    private val searchInterface: SearchInterface,
    private val popularVideoInterface: PopularVideoInterface,
    private val searchOrderInterface: SearchOrderInterface
) : NetworkRepository {
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

    override suspend fun getSearchOrderVideoList(query: String): SearchData {
        return searchOrderInterface.getSearchList(query)
    }

}