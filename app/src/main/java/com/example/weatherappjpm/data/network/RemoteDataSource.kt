package com.example.weatherappjpm.data.network

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: OpenWeatherMapApi) {

    /**
     * Remember to update the data from string to actual data
     */
    suspend fun fetchDataFromApi(): List<String> {
        // Make network call using Retrofit
        return listOf("Network Data")
    }
}