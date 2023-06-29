package com.example.weatherapp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.database.AppDatabase
import com.example.weatherapp.data.database.mapper.WeatherMapper
import com.example.weatherapp.data.network.ApiFactory
import com.example.weatherapp.domain.Repository
import com.example.weatherapp.domain.WeatherEntity
import com.example.weatherapp.domain.NetworkResult

class RepositoryImpl(private val application: Application) : Repository {

    private val _currentWeather = MutableLiveData<NetworkResult<WeatherEntity>>()
    override val currentWeather: LiveData<NetworkResult<WeatherEntity>>
        get() = _currentWeather

    private val _forecast = MutableLiveData<NetworkResult<List<WeatherEntity>>>()
    override val forecast: LiveData<NetworkResult<List<WeatherEntity>>>
        get() = _forecast

    private val currentWeatherDao = AppDatabase.getInstance(application).currentWeatherDao()
    private val forecastDao = AppDatabase.getInstance(application).forecastDao()
    private val apiService = ApiFactory.apiService
    private val errorConverter = ApiFactory.errorConverter
    private val mapper = WeatherMapper()

    override suspend fun loadCurrentWeather() {
        _currentWeather.postValue(NetworkResult.Loading())
        val response = apiService.getCurrentWeather(apiKey = API_KEY)
        if (response.isSuccessful && response.body() != null) {
            val weatherDbModel = mapper.mapCurrentWeatherDtoToDbModel(response.body()!!)
            currentWeatherDao.updateCurrentWeather(weatherDbModel)

            val currentWeather = mapper.mapCurrentWeatherDbModelToEntity(currentWeatherDao.getCurrentWeather())
            _currentWeather.postValue(NetworkResult.Success(currentWeather))
        } else if (response.errorBody() != null) {
            val apiError = errorConverter.convert(response.errorBody()!!)
            val currentWeather = mapper.mapCurrentWeatherDbModelToEntity(currentWeatherDao.getCurrentWeather())
            _currentWeather.postValue(NetworkResult.Error(apiError?.message, currentWeather))
        } else {
            _currentWeather.postValue(NetworkResult.Error("Poor Internet Connection"))
        }
    }

    override suspend fun loadWeatherForecast() {
        _forecast.postValue(NetworkResult.Loading())
        val response = apiService.getWeatherForecast(apiKey = API_KEY)
        Log.d(this.javaClass.simpleName, response.body().toString())
        if (response.isSuccessful && response.body() != null) {
            val forecastDto = response.body()!!
            val forecastDbModel = mapper.mapForecastListDtoToListDbModel(forecastDto.list)
            forecastDao.updateForecast(forecastDbModel)

            val forecast = mapper.mapForecastDbModelListToEntity(forecastDao.getWeatherForecast())
            _forecast.postValue(NetworkResult.Success(forecast))
        } else if (response.errorBody() != null) {
            val apiError = errorConverter.convert(response.errorBody()!!)
            val forecast = mapper.mapForecastDbModelListToEntity(forecastDao.getWeatherForecast())
            _forecast.postValue(NetworkResult.Error(apiError?.message, forecast))
        } else {
            _forecast.postValue(NetworkResult.Error("Poor Internet Connection"))
        }
    }

    companion object {
        private const val API_KEY = "1d20910c48ec5fabb88acc85423ebbe0"
    }
}