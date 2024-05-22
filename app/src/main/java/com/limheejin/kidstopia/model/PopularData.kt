package com.limheejin.kidstopia.model

// pageInfo와 Thumbnails는 받는 데이터가 같으므로 SearchData와 같은 데이터 클래스 사용
data class PopularData(
    val kind: String,
    val etag: String,
    val pageInfo: PageInfo,
    val items: MutableList<PopularItems>
)

data class PopularItems(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: PopularSnippet
)
data class PopularSnippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val tags: MutableList<String>,
    val categoryId: String,
)