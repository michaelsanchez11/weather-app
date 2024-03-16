package com.example.weatherappjpm.data_models

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse (

    @SerializedName("cod")
    var cod: String? = null,

    @SerializedName("message")
    var message: Int? = null,

    @SerializedName("cnt")
    var cnt: Int? = null,

    @SerializedName("list")
    var weatherDataList: ArrayList<WeatherDataListItem> = arrayListOf(),

    @SerializedName("city")
    var city: City? = City()
)
