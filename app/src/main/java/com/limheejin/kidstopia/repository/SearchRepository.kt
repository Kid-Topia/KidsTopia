package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.SearchData


interface SearchRepository {
//    suspend fun getPopularVideoList(): PopularData
    suspend fun getSearchVideoList(query: String): SearchData
}