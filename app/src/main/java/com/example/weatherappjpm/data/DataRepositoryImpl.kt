package com.example.weatherappjpm.data

import com.example.weatherappjpm.data.local.LocalDataSource
import com.example.weatherappjpm.data.network.RemoteDataSource
import com.example.weatherappjpm.data_models.WeatherForecastResponse
import retrofit2.Call
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : DataRepository {
    override suspend fun fetchDataFromApi(cityName: String, apiKey: String): Call<WeatherForecastResponse> {
        return remoteDataSource.fetchDataFromApi(cityName,apiKey)
    }

    override suspend fun fetchDataFromDatabase(): List<String> {
        return localDataSource.fetchDataFromDatabase()
    }
}