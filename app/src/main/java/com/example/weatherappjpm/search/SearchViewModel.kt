package com.example.weatherappjpm.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappjpm.DisplayWeatherData
import com.example.weatherappjpm.data.DataRepository
import com.example.weatherappjpm.data.network.NetworkHelper
import com.example.weatherappjpm.data_models.WeatherForecastResponse
import com.example.weatherappjpm.room.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val networkHelper: NetworkHelper) : ViewModel() {

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private val _networkData = MutableLiveData<DisplayWeatherData?>()
    val networkData: LiveData<DisplayWeatherData?> = _networkData

    private val _localDbData = MutableLiveData<WeatherData?>()
    val localDbData: LiveData<WeatherData?> = _localDbData

    private val _networkConnected = MutableLiveData<Boolean>()
    val networkConnected: LiveData<Boolean> = _networkConnected

    fun setSearchQuery(queryData: String) {
        _searchQuery.value = queryData
    }

    fun setNetworkConnected(isConnected: Boolean) {
        _networkConnected.value = isConnected
    }

    fun fetchNetworkDataByLocation(lat: Double, lon: Double, apiKey: String) {
        if (!networkHelper.isNetworkConnected()) {
            _networkConnected.value = false
            return
        }

        viewModelScope.launch {
            val call = dataRepository.fetchDataWithLocationFromApi(lat, lon, apiKey)
            call.enqueue(object : Callback<WeatherForecastResponse> {
                override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        val weatherData = WeatherData(
                            data?.city?.name.toString(),
                            System.currentTimeMillis(),
                            data?.weatherDataList)

                        val displayData = DisplayWeatherData(
                            data?.city?.name.toString(),
                            data?.weatherDataList)

                        _networkData.postValue(displayData)

                        viewModelScope.launch {
                            dataRepository.insertWeatherData(weatherData)
                        }
                    } else {
                        _networkData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                    Log.d(TAG, "Error fetching weather data", t)
                }
            })
        }
    }

    fun fetchNetworkData(cityName: String, apiKey: String) {
        if (!networkHelper.isNetworkConnected()) {
            _networkConnected.value = false
            return
        }

        viewModelScope.launch {
            val call = dataRepository.fetchDataFromApi(cityName, apiKey)
            call.enqueue(object : Callback<WeatherForecastResponse> {
                override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        val weatherData = WeatherData(
                            data?.city?.name.toString(),
                            System.currentTimeMillis(),
                            data?.weatherDataList)

                        val displayData = DisplayWeatherData(
                            data?.city?.name.toString(),
                            data?.weatherDataList)

                        _networkData.postValue(displayData)

                        viewModelScope.launch {
                            dataRepository.insertWeatherData(weatherData)
                        }
                    } else {
                        _networkData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                    Log.d(TAG, "Error fetching weather data", t)
                }
            })
        }
    }

    fun fetchMostRecentLocationSearched() {
        viewModelScope.launch {
            _localDbData.value = dataRepository.fetchMostRecentWeatherDataFromDatabase()
        }
    }

    companion object {
        const val TAG = "SearchViewModel"
    }
}