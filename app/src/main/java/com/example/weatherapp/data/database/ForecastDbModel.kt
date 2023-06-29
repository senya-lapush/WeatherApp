package com.example.weatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastDbModel(
    @PrimaryKey
    val dt: Long,
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Int,
    val pressure: Int
)