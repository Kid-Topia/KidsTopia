package com.kidstopia.kidstopia.presentation.network

import com.kidstopia.kidstopia.model.ChannelData
import retrofit2.http.GET
import retrofit2.http.Query

interface ChannelInterface {
    @GET("channels")
    suspend fun getChannelData(
        @Query("key") key: String = NetworkClient.AUTH_KEY,
        @Query("part") part: String,
        @Query("id") id: String
    ): ChannelData
}