package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.ChannelData
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.SearchData


interface NetworkRepository {
    suspend fun getPopularVideoList(
        AUTH_KEY: String,
        part: String,
        chart: String,
        maxResults: Int
    ): PopularData
    suspend fun getVideo(
        AUTH_KEY: String,
        part: String,
        videoId: String
    ): PopularData
    suspend fun getChannel(
        AUTH_KEY: String,
        part: String,
        id: String
    ): ChannelData
    suspend fun getSearchVideoList(query: String): SearchData

    suspend fun getSearchOrderVideoList(query: String): SearchData
}