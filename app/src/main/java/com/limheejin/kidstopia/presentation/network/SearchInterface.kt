package com.limheejin.kidstopia.presentation.network

import com.limheejin.kidstopia.model.SearchData
import com.limheejin.kidstopia.presentation.network.NetworkClient.AUTH_KEY
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

//        /* CategoryId
//        15 - Pets & Animals,   1 -  Film & Animation
//        27 - Education,        31 - Anime/Animation
//        37 - Family,           23 - Comedy
//        */