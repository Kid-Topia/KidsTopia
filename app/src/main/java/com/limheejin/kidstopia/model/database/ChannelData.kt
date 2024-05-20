package com.limheejin.kidstopia.model.database

data class ChannelResponse(
    val kind: String?,
    val etag: String?,
    val items: List<ChannelData>
)
data class ChannelData(
    val kind: String?,
    val etag: String?,
    val id: String?,
    val snippet: ChannelSnippet
)

data class ChannelSnippet(
    val title: String?,
    val thumbnails: ChannelThumbnailsHigh?
)

data class ChannelThumbnailsHigh(
    val high: ChannelThumbnails
)
data class ChannelThumbnails(
    val url: String?
)
