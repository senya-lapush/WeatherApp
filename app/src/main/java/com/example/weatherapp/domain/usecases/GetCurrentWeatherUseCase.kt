package com.example.weatherapp.domain.usecases

import com.example.weatherapp.domain.Repository

class GetCurrentWeatherUseCase(private val repository: Repository) {
    operator fun invoke() = repository.currentWeather
}