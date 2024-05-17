package com.limheejin.kidstopia.presentation.network

import android.telecom.Call
import com.limheejin.kidstopia.model.PopularData
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularVideoInterface {
    @GET("videos")
    suspend fun getPopularVideoList(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
        @Query("regionCode") regionCode: String,
    ): PopularData
}