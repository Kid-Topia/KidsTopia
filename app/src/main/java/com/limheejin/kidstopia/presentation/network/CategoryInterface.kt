package com.limheejin.kidstopia.presentation.network

import com.limheejin.kidstopia.model.PopularData
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryInterface {
    @GET("videoCategories")
    suspend fun getCategoryList(
        @Query("key") key: String = NetworkClient.AUTH_KEY,
        @Query("part") part: String = "snippet",
        @Query("regionCode") regionCode: String,
    )
}

interface PopularVideoCategoryInterface {
    @GET("videos")
    suspend fun getPopularVideoCategoryList(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("videoCategoryId") categoryId: String,
        @Query("maxResults") maxResults: Int,
    ): PopularData
}