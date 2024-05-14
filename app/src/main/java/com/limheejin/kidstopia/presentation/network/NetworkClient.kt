package com.limheejin.kidstopia.presentation.network

import com.limheejin.kidstopia.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {
    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    const val AUTH_KEY = "AIzaSyAcsaCoyshiKfP7jKGFXu_JUrFLTdVb_N8"

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val retrofitBase = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()


    val youtubeApiSearch: SearchInterface = retrofitBase.create(SearchInterface::class.java)
    val youtubeApiPopularVideo: PopularVideoInterface = retrofitBase.create(PopularVideoInterface::class.java)
    val youtubeApiChannels: ChannelsInterface = retrofitBase.create(ChannelsInterface::class.java)
}