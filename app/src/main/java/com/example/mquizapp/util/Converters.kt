package com.example.mquizapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    // as room does not support the list<String> we need type converters

    @TypeConverter
    fun fromStringList(value : List<String>) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value : String) : List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

}