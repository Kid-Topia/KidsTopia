package com.limheejin.kidstopia.model



data class SearchData(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val regionCode: String,
    val pageInfo: PageInfo,
    val items: MutableList<SearchItems>
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class SearchItems(
    val kind: String,
    val etag: String,
    val id: IDdata,
    val snippet: SearchSnippet
)

data class IDdata(
    val kind: String,
    val videoId: String
)

data class SearchSnippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String
)

data class Thumbnails(
    val default: Default,
    val medium: Medium,
    val high: High,
    val maxres: Maxres?
)

data class Default(
    val url: String,
    val width: Int,
    val height: Int
)

data class Medium(
    val url: String,
    val width: Int,
    val height: Int
)

data class High(
    val url: String,
    val width: Int,
    val height: Int
)

data class Maxres(
    val url: String,
    val width: Int,
    val height: Int
)