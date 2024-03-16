package com.example.weatherappjpm.data.network

import com.example.weatherappjpm.data_models.WeatherForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi  {
    @GET("data/2.5/forecast")
    fun getWeatherForecastWithLatLong(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Call<WeatherForecastResponse>

    @GET("data/2.5/forecast")
    fun getWeatherForecastWithCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Call<WeatherForecastResponse>
}