package com.limheejin.kidstopia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.SearchData
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity


interface Repository {
//    suspend fun getPopularVideoList(): PopularData
    suspend fun getSearchVideoList(query: String): SearchData
}