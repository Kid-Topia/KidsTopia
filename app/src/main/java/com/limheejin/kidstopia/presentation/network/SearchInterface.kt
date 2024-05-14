package com.limheejin.kidstopia.presentation.network

import com.limheejin.kidstopia.model.SearchData
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchInterface {
    @GET("search")
    suspend fun getSearchList(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("safeSearch") safeSearch: String,
        @Query("type") type: String,
        @Query("maxResults") maxResults: Int,
        @Query("q") query: String,
        @Query("videoCategoryId") videoCategoryId: String
    ): SearchData
}