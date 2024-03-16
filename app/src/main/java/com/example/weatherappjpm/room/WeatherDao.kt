package com.example.weatherappjpm.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherData)

    @Query("SELECT * FROM weather_data WHERE cityId = :cityId")
    suspend fun getWeatherData(cityId: String): WeatherData?

    @Query("SELECT * FROM weather_data ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestWeatherData(): WeatherData?
}