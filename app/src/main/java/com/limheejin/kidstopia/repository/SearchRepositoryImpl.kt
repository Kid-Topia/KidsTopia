package com.limheejin.kidstopia.repository

import com.limheejin.kidstopia.model.SearchData
import com.limheejin.kidstopia.presentation.network.SearchInterface

class SearchRepositoryImpl(
//    private val popularVideoInterface : PopularVideoInterface,
    private val searchInterface: SearchInterface,
): SearchRepository {
//    override suspend fun getPopularVideoList(): PopularData {
//        return popularVideoInterface.getPopularVideoList()
//    }
    override suspend fun getSearchVideoList(query: String): SearchData {
        return searchInterface.getSearchList(query)
    }

}