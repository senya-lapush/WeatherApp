package com.example.weatherapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.RepositoryImpl
import com.example.weatherapp.domain.usecases.GetCurrentWeatherUseCase
import com.example.weatherapp.domain.usecases.GetWeatherForecastUseCase
import com.example.weatherapp.domain.usecases.LoadCurrentWeatherUseCase
import com.example.weatherapp.domain.usecases.LoadForecastUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getWeatherForecastUseCase = GetWeatherForecastUseCase(repository)
    private val getCurrentWeatherUseCase = GetCurrentWeatherUseCase(repository)
    private val loadCurrentWeatherUseCase = LoadCurrentWeatherUseCase(repository)
    private val loadForecastUseCase = LoadForecastUseCase(repository)

    val currentWeather = getCurrentWeatherUseCase()
    val forecast = getWeatherForecastUseCase()

    fun loadCurrentWeather() {
        viewModelScope.launch {
            loadCurrentWeatherUseCase()
        }
    }

    fun loadForecast() {
        viewModelScope.launch {
            loadForecastUseCase()
        }
    }
}