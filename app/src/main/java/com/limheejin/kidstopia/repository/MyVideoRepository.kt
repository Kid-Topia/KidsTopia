package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity

interface MyVideoRepository {
    fun insertVideo(myFavoriteVideo: MyFavoriteVideoEntity)
    fun deleteVideo(video_id: String)
    fun getAllVideo(): MutableList<MyFavoriteVideoEntity>
}