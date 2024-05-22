package com.kidstopia.kidstopia.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyFavoriteVideoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(myFavoriteVideo: MyFavoriteVideoEntity)

    @Query("SELECT * FROM video_table")
    suspend fun getAllVideo(): MutableList<MyFavoriteVideoEntity>

    @Query("SELECT date FROM video_table WHERE video_id =:video_id")
    suspend fun getVideoDate(video_id: String): String

    @Query("SELECT isLikedDate FROM video_table WHERE video_id =:video_id")
    suspend fun getVideoLikedDate(video_id: String): String

    @Query("SELECT classify FROM video_table WHERE video_id =:video_id")
    suspend fun getVideoClassify(video_id: String): String

    @Query("DELETE FROM video_table WHERE video_id = :video_id")
    fun deleteVideo(video_id: String)

    @Query("DELETE FROM video_table WHERE classify = 'isVisited'")
    fun deleteVisitedVideo()

    @Query("DELETE FROM video_table WHERE classify = 'isLiked'")
    fun deleteLikedVideo()
}