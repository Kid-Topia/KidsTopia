package com.kidstopia.kidstopia.presentation.network

import com.kidstopia.kidstopia.model.SearchData
import com.kidstopia.kidstopia.presentation.network.NetworkClient.AUTH_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchInterface {
    @GET("search")
    suspend fun getSearchList(
        @Query("q") query: String,
        @Query("key") key: String = AUTH_KEY,
        @Query("part") part: String = "snippet",
        @Query("safeSearch") safeSearch: String = "strict",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 8,
        @Query("videoCategoryId") videoCategoryId: String = "15"
    ): SearchData
}

interface SearchOrderInterface {
    @GET("search")
    suspend fun getSearchList(
        @Query("q") query: String,
        @Query("key") key: String = AUTH_KEY,
        @Query("part") part: String = "snippet",
        @Query("safeSearch") safeSearch: String = "strict",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 8,
        @Query("order") order: String = "viewCount",
        @Query("regionCode") regionCode: String = "KR"
    ): SearchData
}
