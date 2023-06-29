package com.example.weatherapp.data.database

import androidx.room.*

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM current_weather LIMIT 1")
    suspend fun getCurrentWeather(): CurrentWeatherDbModel

    suspend fun updateCurrentWeather(currentWeather: CurrentWeatherDbModel) {
        deleteCurrentWeather()
        insertCurrentWeather(currentWeather)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherDbModel)

    @Query("DELETE FROM current_weather")
    suspend fun deleteCurrentWeather()
}