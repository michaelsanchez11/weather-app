package com.example.weatherappjpm.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappjpm.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private val _networkData = MutableLiveData<List<String>>()
    val networkData: LiveData<List<String>> = _networkData

    private val _localDbData = MutableLiveData<List<String>>()
    val localDbData: LiveData<List<String>> = _localDbData

    fun setSearchQuery(queryData: String) {
        _searchQuery.value = queryData
    }

    fun fetchNetworkData() {
        viewModelScope.launch {
            _networkData.value = dataRepository.fetchDataFromApi()
        }
    }

    fun fetchLocalDbData() {
        viewModelScope.launch {
            _localDbData.value = dataRepository.fetchDataFromDatabase()
        }
    }
}