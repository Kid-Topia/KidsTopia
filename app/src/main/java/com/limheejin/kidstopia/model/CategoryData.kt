package com.limheejin.kidstopia.model

data class CategoryData(
    val kind: String,
    val etag: String,
    val items: CategoryItem,
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

// 31  애니메이션 15 애완동물 및 동물 19 여행 이벤트 27 교육
// 10 음악

