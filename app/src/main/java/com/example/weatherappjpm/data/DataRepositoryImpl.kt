package com.example.weatherappjpm.data

import com.example.weatherappjpm.data.local.LocalDataSource
import com.example.weatherappjpm.data.network.RemoteDataSource
import com.example.weatherappjpm.data_models.WeatherForecastResponse
import com.example.weatherappjpm.room.WeatherData
import retrofit2.Call
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : DataRepository {
    override suspend fun fetchDataFromApi(cityName: String, apiKey: String): Call<WeatherForecastResponse> {
        return remoteDataSource.fetchDataFromApi(cityName,apiKey)
    }

    override suspend fun fetchMostRecentWeatherDataFromDatabase(): WeatherData? {
        return localDataSource.fetchMostRecentWeatherDataFromDatabase()
    }

    override suspend fun insertWeatherData(weatherData: WeatherData) {
        localDataSource.insertWeatherData(weatherData)
    }

    override suspend fun fetchDataWithLocationFromApi(lat: Double, lon: Double, apiKey: String): Call<WeatherForecastResponse> {
        return remoteDataSource.fetchDataWithLocationFromApi(lat, lon, apiKey)
    }
}