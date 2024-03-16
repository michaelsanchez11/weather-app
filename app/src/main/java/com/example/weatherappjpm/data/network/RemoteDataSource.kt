package com.example.weatherappjpm.data.network

import com.example.weatherappjpm.data_models.WeatherForecastResponse
import retrofit2.Call
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: OpenWeatherMapApi) {

    fun fetchDataFromApi(cityName: String, apiKey: String): Call<WeatherForecastResponse> {
        // Make network call using Retrofit
        return apiService.getWeatherForecastWithCityName(cityName, apiKey)
    }

    fun fetchDataWithLocationFromApi(lat: Double, lon: Double, apiKey: String): Call<WeatherForecastResponse> {
        return apiService.getWeatherForecastWithLatLong(lat, lon, apiKey)
    }
}