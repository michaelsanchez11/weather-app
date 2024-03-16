package com.example.weatherappjpm.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherappjpm.data_models.WeatherDataListItem

@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey
    val cityId: String,

    val timestamp: Long = System.currentTimeMillis(),

    @TypeConverters(WeatherTypeConverter::class)
    val weatherDataList: ArrayList<WeatherDataListItem>?,
)