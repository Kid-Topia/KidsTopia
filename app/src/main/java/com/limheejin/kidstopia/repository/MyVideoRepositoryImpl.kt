package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity

class MyVideoRepositoryImpl(
    private val myFavoriteVideoDao: MyFavoriteVideoDAO
): MyVideoRepository {
    override fun insertVideo(myFavoriteVideo: MyFavoriteVideoEntity) {
        myFavoriteVideoDao.insertVideo(myFavoriteVideo)
    }

    override fun deleteVideo(video_id: String) {
        myFavoriteVideoDao.deleteVideo(video_id)
    }

    override fun getAllVideo(): MutableList<MyFavoriteVideoEntity> {
        return myFavoriteVideoDao.getAllVideo()
    }

}