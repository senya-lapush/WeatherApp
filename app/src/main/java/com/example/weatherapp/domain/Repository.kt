package com.example.weatherapp.domain

import androidx.lifecycle.LiveData

interface Repository {

    val currentWeather: LiveData<NetworkResult<WeatherEntity>>
    val forecast: LiveData<NetworkResult<List<WeatherEntity>>>

    suspend fun loadCurrentWeather()
    suspend fun loadWeatherForecast()
}