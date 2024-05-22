package com.kidstopia.kidstopia.model

data class CategoryData(
    val kind: String,
    val etag: String,
    val items: List<CategoryItem>,
)
data class CategoryItem(
    val id: String,
    val snippet: CategorySnippet
)

data class CategorySnippet(
    val title: String,
    val channelld: String,
    val assignable: Boolean
)


