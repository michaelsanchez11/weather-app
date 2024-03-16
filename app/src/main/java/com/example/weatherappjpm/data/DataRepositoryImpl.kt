package com.example.weatherappjpm.data

import com.example.weatherappjpm.data.local.LocalDataSource
import com.example.weatherappjpm.data.network.RemoteDataSource
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : DataRepository {
    override suspend fun fetchDataFromApi(): List<String> {
        return remoteDataSource.fetchDataFromApi()
    }

    override suspend fun fetchDataFromDatabase(): List<String> {
        return localDataSource.fetchDataFromDatabase()
    }
}