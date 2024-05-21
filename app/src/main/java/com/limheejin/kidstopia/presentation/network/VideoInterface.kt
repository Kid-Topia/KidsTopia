package com.limheejin.kidstopia.presentation.network


import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.SearchData
import com.limheejin.kidstopia.presentation.network.NetworkClient.AUTH_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularVideoInterface {
    @GET("videos")
    suspend fun getPopularVideoList(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
    ): PopularData
}

interface VideoInterface {
    @GET("videos")
    suspend fun getVideoData(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("id") id: String
    ): PopularData
}

interface PopularVideoCategoryInterface {
    @GET("videos")
    suspend fun getVideoCategoryList(
        @Query("q") query: String,
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("safeSearch") safeSearch: String = "strict",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 8,
    ): SearchData
}