package com.limheejin.kidstopia.model.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyFavoriteVideoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(myFavoriteVideo: MyFavoriteVideoEntity)

    @Query("SELECT * FROM video_table")
    fun getAllVideo(): MutableList<MyFavoriteVideoEntity>

    @Query("DELETE FROM video_table WHERE video_id = :video_id")
    fun deleteVideo(video_id: String)

}