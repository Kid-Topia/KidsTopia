package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.PopularData
import com.limheejin.kidstopia.model.SearchData


interface Repository {
//    suspend fun getPopularVideoList(): PopularData
    suspend fun getSearchVideoList(query: String): SearchData
}