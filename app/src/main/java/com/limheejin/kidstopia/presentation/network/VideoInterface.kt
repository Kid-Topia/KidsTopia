package com.limheejin.kidstopia.presentation.network

import com.limheejin.kidstopia.model.PopularData
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoInterface {
    @GET("videos")
    suspend fun getVideoData(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("id") id: String
    ): PopularData
}

interface PopularVideoInterface {
    @GET("videos")
    suspend fun getPopularVideoList(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
    ): PopularData
}

interface CategoryPopularVideoInterface {
    @GET("videos")
    suspend fun getCategoryPopularVideoList(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("videoCategoryId") categoryId: String,
        @Query("maxResults") maxResults: Int,
    ): PopularData
}