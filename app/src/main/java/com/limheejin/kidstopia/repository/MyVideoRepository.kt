package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import kotlinx.coroutines.flow.Flow

interface MyVideoRepository {
    suspend fun insertVideo(myFavoriteVideo: MyFavoriteVideoEntity)
    suspend fun deleteVideo(video_id: String)
    suspend fun getAllVideo(): MutableList<MyFavoriteVideoEntity>
    suspend fun getVideoClassify(video_id: String) : String
    suspend fun getIsLikedDate(video_id: String): String
    suspend fun getDate(video_id: String): String

}