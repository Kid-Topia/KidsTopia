package com.limheejin.kidstopia.model.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.gson.Gson

@Database(entities = [MyFavoriteVideoEntity::class], exportSchema = false, version = 1)
abstract class MyFavoriteVideoDatabase: RoomDatabase() {
    abstract fun getDao(): MyFavoriteVideoDAO

    companion object {
        private var INSTANCE: MyFavoriteVideoDatabase? = null

        fun getDatabase(application: Context) : MyFavoriteVideoDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    application, MyFavoriteVideoDatabase::class.java, "video_database")
                    .build()
            }
            return INSTANCE as MyFavoriteVideoDatabase
        }
    }
}