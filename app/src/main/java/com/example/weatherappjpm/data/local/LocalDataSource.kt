package com.example.weatherappjpm.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor() {

    suspend fun fetchDataFromDatabase(): List<String> {
        // Fetch data from Room database
        return listOf("Local Data")
    }
}