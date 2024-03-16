package com.example.weatherappjpm.search

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappjpm.data.DataRepository
import com.example.weatherappjpm.data.network.NetworkHelper
import com.example.weatherappjpm.data_models.WeatherForecastResponse
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

    private val _networkData = MutableLiveData<List<String>>()
    val networkData: LiveData<List<String>> = _networkData

    private val _localDbData = MutableLiveData<List<String>>()
    val localDbData: LiveData<List<String>> = _localDbData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _networkConnected = MutableLiveData<Boolean>()
    val networkConnected: LiveData<Boolean> = _networkConnected

    fun setSearchQuery(queryData: String) {
        _searchQuery.value = queryData
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
                        Log.d("Michael", "It worked $data")
                    }
                    _isLoading.postValue(false)
                }

                override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                    Log.d("Michael", "Error fetching weather data", t)
                    _isLoading.postValue(false)
                }
            })
        }
    }

    fun fetchLocalDbData() {
        viewModelScope.launch {
            _localDbData.value = dataRepository.fetchDataFromDatabase()
        }
    }

    companion object {
        const val TAG = "SearchViewModel"
    }
}