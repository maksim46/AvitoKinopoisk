package com.max_ro.avitokinopoisk.data.database.Converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String>?): String? {
        return gson.toJson(list)
    }
}