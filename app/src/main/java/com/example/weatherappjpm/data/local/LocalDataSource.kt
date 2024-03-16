package com.example.weatherappjpm.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor() {

    fun fetchDataFromDatabase(): List<String> {
        // Fetch data from Room database
        return listOf("Local Data")
    }
}