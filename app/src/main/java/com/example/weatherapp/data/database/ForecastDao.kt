package com.example.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast")
    suspend fun getWeatherForecast(): List<ForecastDbModel>

    suspend fun updateForecast(forecast: List<ForecastDbModel>) {
        deleteForecast()
        insertForecast(forecast)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: List<ForecastDbModel>)

    @Query("DELETE FROM forecast")
    suspend fun deleteForecast()
}