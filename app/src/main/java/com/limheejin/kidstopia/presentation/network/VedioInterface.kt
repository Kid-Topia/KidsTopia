package com.limheejin.kidstopia.presentation.network

import com.limheejin.kidstopia.model.PopularData
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularVedioInterface {
    @GET("videos")
    suspend fun getPopularVideoList(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int
    ): PopularData
}