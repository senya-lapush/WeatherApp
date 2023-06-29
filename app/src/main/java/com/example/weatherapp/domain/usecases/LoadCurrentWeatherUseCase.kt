package com.example.weatherapp.domain.usecases

import com.example.weatherapp.domain.Repository

class LoadCurrentWeatherUseCase(private val repository: Repository) {
    suspend operator fun invoke() = repository.loadCurrentWeather()
}