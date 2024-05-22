package com.kidstopia.kidstopia.repository

import com.kidstopia.kidstopia.model.database.MyFavoriteVideoEntity

interface RoomRepository {
    suspend fun insertVideo(myFavoriteVideo: MyFavoriteVideoEntity)
    suspend fun deleteVideo(video_id: String)
    suspend fun getAllVideo(): MutableList<MyFavoriteVideoEntity>
    suspend fun getVideoClassify(video_id: String): String
    suspend fun getIsLikedDate(video_id: String): String
    suspend fun getDate(video_id: String): String
}