package com.limheejin.kidstopia.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.util.query
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.SearchData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.network.PopularVideoInterface
import com.limheejin.kidstopia.presentation.network.SearchInterface
import com.limheejin.kidstopia.presentation.network.VideoInterface

class RepositoryImpl(
    private val videoInterface : VideoInterface,
    private val searchInterface: SearchInterface,
): Repository {

    override suspend fun getVideo(AUTH_KEY: String, part: String, videoId: String): PopularData {
        return videoInterface.getVideoData(
            AUTH_KEY,
            part,
            videoId
        )
    }

    override suspend fun getSearchVideoList(query: String): SearchData {
        return searchInterface.getSearchList(query)
    }

}