package com.example.weatherappjpm.data

import com.example.weatherappjpm.data_models.WeatherForecastResponse
import com.example.weatherappjpm.room.WeatherData
import retrofit2.Call

interface DataRepository {

    /**
        Reminder to swap out string with actual data model class
     */
    suspend fun fetchDataFromApi(cityName: String, apiKey: String): Call<WeatherForecastResponse>
    suspend fun fetchDataWithLocationFromApi(lat: Double, lon: Double, apiKey: String): Call<WeatherForecastResponse>
    suspend fun fetchMostRecentWeatherDataFromDatabase(): WeatherData?
    suspend fun insertWeatherData(weatherData: WeatherData)
}