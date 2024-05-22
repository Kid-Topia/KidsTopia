package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity

class RoomRepositoryImpl(
    private val myFavoriteVideoDao: MyFavoriteVideoDAO
) : RoomRepository {
    override suspend fun insertVideo(myFavoriteVideo: MyFavoriteVideoEntity) {
        myFavoriteVideoDao.insertVideo(myFavoriteVideo)
    }

    override suspend fun deleteVideo(video_id: String) {
        myFavoriteVideoDao.deleteVideo(video_id)
    }

    override suspend fun getAllVideo(): MutableList<MyFavoriteVideoEntity> {
        return myFavoriteVideoDao.getAllVideo()
    }

    override suspend fun getVideoClassify(video_id: String): String {
        return myFavoriteVideoDao.getVideoClassify(video_id)
    }

    override suspend fun getIsLikedDate(video_id: String): String {
        return myFavoriteVideoDao.getVideoLikedDate(video_id)
    }

    override suspend fun getDate(video_id: String): String {
        return myFavoriteVideoDao.getVideoDate(video_id)
    }
}