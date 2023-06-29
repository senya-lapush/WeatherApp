package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.ForecastDto
import com.example.weatherapp.data.model.CurrentWeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query(QUERY_PARAM_CITY_COUNTRY) cityAndCountry: String = "Saint Petersburg, ru",
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_UNITS) units: String = "metric"
    ): Response<CurrentWeatherDto>

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query(QUERY_PARAM_CITY_COUNTRY) cityAndCountry: String = "Saint Petersburg, ru",
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_UNITS) units: String = "metric"
    ): Response<ForecastDto>

    companion object {
        private const val QUERY_PARAM_CITY_COUNTRY = "q"
        private const val QUERY_PARAM_API_KEY = "APPID"
        private const val QUERY_PARAM_UNITS = "units"
    }
}