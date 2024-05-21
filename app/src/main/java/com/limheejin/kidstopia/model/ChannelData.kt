package com.limheejin.kidstopia.model


data class ChannelData(
    val kind: String,
    val etag: String,
    val items: List<ChannelItems>
)

data class ChannelItems(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: ChannelSnippet,
    val statistics: ChannelStatistics
)

data class ChannelSnippet(
    val title: String,
    val description: String?,
    val thumbnails: Thumbnails,
)

data class ChannelStatistics(
    val subscriberCount: Long,
    val videoCount: Long
)