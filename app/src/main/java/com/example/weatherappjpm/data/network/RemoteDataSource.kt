package com.example.weatherappjpm.data.network

import com.example.weatherappjpm.data_models.WeatherForecastResponse
import retrofit2.Call
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: OpenWeatherMapApi) {

    /**
     * Remember to update the data from string to actual data
     */
    fun fetchDataFromApi(cityName: String, apiKey: String): Call<WeatherForecastResponse> {
        // Make network call using Retrofit
        return apiService.getWeatherForecastWithCityName(cityName, apiKey)
    }
}