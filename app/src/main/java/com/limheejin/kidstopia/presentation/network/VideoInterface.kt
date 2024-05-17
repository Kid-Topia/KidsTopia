package com.limheejin.kidstopia.presentation.network

import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.presentation.network.NetworkClient.AUTH_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularVideoInterface {
    @GET("videos")
    suspend fun getPopularVideoList(
        @Query("key") key: String = AUTH_KEY,
        @Query("part") part: String = "snippet, contentDetails",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResults") maxResults: Int = 10
    ): PopularData
}