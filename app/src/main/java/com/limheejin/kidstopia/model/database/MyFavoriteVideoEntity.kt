package com.limheejin.kidstopia.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.Url
import java.time.LocalDateTime

@Entity(tableName = "video_table")
data class MyFavoriteVideoEntity(
    @PrimaryKey
    @ColumnInfo(name = "video_id")
    val id: String,
    val title: String?,
    val ChannelId: String?,
    val thumbnails: String?,
    val date: String?,
    val classify: String,
    val isLikedDate: String?
)
