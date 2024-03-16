package com.example.weatherappjpm.data

import com.example.weatherappjpm.data_models.WeatherForecastResponse
import retrofit2.Call

interface DataRepository {

    /**
        Reminder to swap out string with actual data model class
     */
    suspend fun fetchDataFromApi(cityName: String, apiKey: String): Call<WeatherForecastResponse>
    suspend fun fetchDataFromDatabase(): List<String>
}