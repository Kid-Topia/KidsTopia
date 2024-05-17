package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import kotlinx.coroutines.flow.Flow

class MyVideoRepositoryImpl(
    private val myFavoriteVideoDao: MyFavoriteVideoDAO
): MyVideoRepository {
    override suspend fun insertVideo(myFavoriteVideo: MyFavoriteVideoEntity) {
        myFavoriteVideoDao.insertVideo(myFavoriteVideo)
    }

    override suspend fun deleteVideo(video_id: String) {
        myFavoriteVideoDao.deleteVideo(video_id)
    }

    override suspend fun getAllVideo(): MutableList<MyFavoriteVideoEntity> {
        return myFavoriteVideoDao.getAllVideo()
    }
}