package com.example.weatherappjpm.di

import android.content.Context
import androidx.room.Room
import com.example.weatherappjpm.data.DataRepository
import com.example.weatherappjpm.data.DataRepositoryImpl
import com.example.weatherappjpm.data.network.OpenWeatherMapApi
import com.example.weatherappjpm.room.AppDatabase
import com.example.weatherappjpm.room.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.openweathermap.org/"
    @Provides
    fun provideDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository {
        return dataRepositoryImpl
    }
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherMapApi(retrofit: Retrofit): OpenWeatherMapApi {
        return retrofit.create(OpenWeatherMapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: AppDatabase): WeatherDao {
        return database.weatherDao()
    }
}