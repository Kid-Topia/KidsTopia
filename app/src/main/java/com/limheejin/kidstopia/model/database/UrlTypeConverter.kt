package com.limheejin.kidstopia.model.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import retrofit2.http.Url

@ProvidedTypeConverter
class UrlTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun urlToString(url: Url?): String {
        return gson.toJson(url)
    }

    @TypeConverter
    fun stringToUrl(string: String?): Url? {
        return gson.fromJson(string, Url::class.java)
    }
}