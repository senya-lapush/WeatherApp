package com.example.weatherapp.domain

data class WeatherEntity(
    val city: String? = null,
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Int,
    val pressure: Int,
    val feelsLike: Int? = null,
    val tempMin: Int? = null,
    val tempMax: Int? = null,
    val visibility: Int? = null,
    val sunrise: String? = null,
    val sunset: String? = null,
    val clouds: Int? = null,
    val windDirection: String? = null,
    val dt: String? = null
)