package com.example.weatherappjpm.data

interface DataRepository {

    /**
        Reminder to swap out string with actual data model class
     */
    suspend fun fetchDataFromApi(): List<String>
    suspend fun fetchDataFromDatabase(): List<String>
}