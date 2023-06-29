package com.example.weatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherDbModel(
    @PrimaryKey
    val dt: Long,
    val city: String,
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Int,
    val pressure: Int,
    val feelsLike: Int?,
    val tempMin: Int?,
    val tempMax: Int?,
    val visibility: Int?,
    val sunrise: Long?,
    val sunset: Long?,
    val clouds: Int?,
    val windDirection: Int?
)