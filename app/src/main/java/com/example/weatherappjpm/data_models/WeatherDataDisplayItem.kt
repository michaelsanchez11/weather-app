package com.example.weatherappjpm.data_models


/**
 * Data class that holds all values we want to display to user.
 */
data class WeatherDataDisplayItem (
    val day: String,
    val description: String,
    val icon: String,
    val windDirection: String,
    val windSpeed: String
)