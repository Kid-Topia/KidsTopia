package com.kidstopia.kidstopia.presentation.network

import com.kidstopia.kidstopia.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {
    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    const val AUTH_KEY = "AIzaSyDDdGC-idaPyf4f_k667l_gISrzhFj-qvk"

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
    val youtubeApiVideo: VideoInterface = retrofitBase.create(VideoInterface::class.java)
    val youtubeApiPopularVideo: PopularVideoInterface =
        retrofitBase.create(PopularVideoInterface::class.java)
    val youtubeApiChannel: ChannelInterface = retrofitBase.create(ChannelInterface::class.java)
    val youtubeApiOrderSearch: SearchOrderInterface =
        retrofitBase.create(SearchOrderInterface::class.java)
}