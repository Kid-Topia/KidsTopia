package com.limheejin.kidstopia.presentation.network

import com.limheejin.kidstopia.model.database.ChannelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ChannelsInterface {
    @GET("channels")
    suspend fun getChannels(
        @Query("key") key: String = NetworkClient.AUTH_KEY,
        @Query("part") part: String,
        @Query("id") id: String
    ): ChannelResponse
}