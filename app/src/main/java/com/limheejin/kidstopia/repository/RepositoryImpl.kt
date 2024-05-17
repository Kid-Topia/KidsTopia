package com.limheejin.kidstopia.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.SearchData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity
import com.limheejin.kidstopia.presentation.network.PopularVideoInterface
import com.limheejin.kidstopia.presentation.network.SearchInterface

class RepositoryImpl(
//    private val popularVideoInterface : PopularVideoInterface,
    private val searchInterface: SearchInterface,
): Repository {
//    override suspend fun getPopularVideoList(): PopularData {
//        return popularVideoInterface.getPopularVideoList()
//    }
    override suspend fun getSearchVideoList(query: String): SearchData {
        return searchInterface.getSearchList(query)
    }

}