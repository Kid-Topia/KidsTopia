package com.limheejin.kidstopia.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [MyFavoriteVideoEntity::class], exportSchema = false, version = 2)
abstract class MyFavoriteVideoDatabase: RoomDatabase() {
    abstract fun getDao(): MyFavoriteVideoDAO

    companion object {
        private var INSTANCE: MyFavoriteVideoDatabase? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE video_table ADD COLUMN 'isLikedDate' TEXT")
            }
        }

        fun getDatabase(context: Context) : MyFavoriteVideoDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, MyFavoriteVideoDatabase::class.java, "video_database")
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
            return INSTANCE as MyFavoriteVideoDatabase
        }
    }
}