package com.kidstopia.kidstopia.model.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.Date

@ProvidedTypeConverter
class DateTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun dateToString(date: Date?): String {
        return gson.toJson(date)
    }

    @TypeConverter
    fun stringToDate(string: String?): Date? {
        return gson.fromJson(string, Date::class.java)
    }
}