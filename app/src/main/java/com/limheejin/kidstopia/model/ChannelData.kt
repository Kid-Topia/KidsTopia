package com.limheejin.kidstopia.model

data class ChannelData (
    val kind: String,
    val etag: String,
    val pageInfo: PageInfo,
    val items: MutableList<ChnnelItem>
)

data class ChnnelItem(
    val thumbnails: String,
    val id: String,
    val title: String
)