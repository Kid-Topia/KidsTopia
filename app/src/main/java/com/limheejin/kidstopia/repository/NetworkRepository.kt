package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.SearchData


interface NetworkRepository {
//    suspend fun getPopularVideoList(): PopularData
    suspend fun getSearchVideoList(query: String): SearchData
}