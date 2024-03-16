package com.example.weatherappjpm.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WeatherData::class], version = 1, exportSchema = false)
@TypeConverters(WeatherTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}