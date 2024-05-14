package com.limheejin.kidstopia.presentation.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ChannelsInterface {
    @GET("channels")
    suspend fun getChannels(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("id") id: String
    )
}