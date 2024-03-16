package com.example.weatherappjpm.room

import androidx.room.TypeConverter
import com.example.weatherappjpm.data_models.WeatherDataListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {
    @TypeConverter
    fun fromWeatherList(value: ArrayList<WeatherDataListItem>): String {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherDataListItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toWeatherList(value: String): ArrayList<WeatherDataListItem> {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherDataListItem>>() {}.type
        return gson.fromJson(value, type)
    }
}