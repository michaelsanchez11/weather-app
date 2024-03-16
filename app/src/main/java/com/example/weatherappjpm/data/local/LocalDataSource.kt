package com.example.weatherappjpm.data.local

import com.example.weatherappjpm.room.WeatherDao
import com.example.weatherappjpm.room.WeatherData
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val weatherDao: WeatherDao) {

    suspend fun fetchMostRecentWeatherDataFromDatabase(): WeatherData? {
        return weatherDao.getLatestWeatherData()
    }

    suspend fun insertWeatherData(weatherData: WeatherData) {
        weatherDao.insertWeatherData(weatherData)
    }
}