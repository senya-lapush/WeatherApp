package com.example.weatherapp.data.database.mapper

import com.example.weatherapp.data.database.CurrentWeatherDbModel
import com.example.weatherapp.data.database.ForecastDbModel
import com.example.weatherapp.data.model.CurrentWeatherDto
import com.example.weatherapp.domain.WeatherEntity
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class WeatherMapper {

    fun mapCurrentWeatherDbModelToEntity(dbModel: CurrentWeatherDbModel) = WeatherEntity(
        city = dbModel.city,
        temperature = dbModel.temperature,
        humidity = dbModel.humidity,
        windSpeed = dbModel.windSpeed,
        pressure = convertHPAToMMHG(dbModel.pressure),
        feelsLike = dbModel.feelsLike,
        tempMin = dbModel.tempMin,
        tempMax = dbModel.tempMax,
        visibility = convertMetresToKilometres(dbModel.visibility),
        sunrise = convertTimestampToDate(dbModel.sunrise, ONLY_TIME_PATTERN),
        sunset = convertTimestampToDate(dbModel.sunset, ONLY_TIME_PATTERN),
        clouds = dbModel.clouds,
        windDirection = convertDegreesToCardinalDirection(dbModel.windDirection),
        dt = convertTimestampToDate(dbModel.dt, DATE_TIME_PATTERN)
    )

    fun mapCurrentWeatherDtoToDbModel(dto: CurrentWeatherDto) = CurrentWeatherDbModel(
        city = dto.name,
        temperature = dto.main.temp.toInt(),
        humidity = dto.main.humidity,
        windSpeed = dto.wind.speed.toInt(),
        pressure = dto.main.pressure,
        feelsLike = dto.main.feels_like?.toInt(),
        tempMin = dto.main.temp_min?.toInt(),
        tempMax = dto.main.temp_max?.toInt(),
        visibility = dto.visibility,
        sunrise = dto.sys?.sunrise,
        sunset = dto.sys?.sunset,
        clouds = dto.clouds?.all,
        windDirection = dto.wind.deg,
        dt = dto.dt
    )

    private fun mapForecastDbModelToEntity(dbModel: ForecastDbModel) = WeatherEntity(
        temperature = dbModel.temperature,
        humidity = dbModel.humidity,
        windSpeed = dbModel.windSpeed,
        pressure = convertHPAToMMHG(dbModel.pressure),
        dt = convertTimestampToDate(dbModel.dt, DATE_TIME_PATTERN)
    )

    private fun mapForecastDtoToDbModel(dto: CurrentWeatherDto) = ForecastDbModel(
        temperature = dto.main.temp.toInt(),
        humidity = dto.main.humidity,
        windSpeed = dto.wind.speed.toInt(),
        pressure = dto.main.pressure,
        dt = dto.dt
    )

    fun mapForecastDbModelListToEntity(list: List<ForecastDbModel>) = list.map {
        mapForecastDbModelToEntity(it)
    }

    fun mapForecastListDtoToListDbModel(list: List<CurrentWeatherDto>) = list.map {
        mapForecastDtoToDbModel(it)
    }

    private fun convertTimestampToDate(timestamp: Long?, pattern: String): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun convertHPAToMMHG(hpa: Int): Int {
        return (hpa * MMHG_IN_ONE_HPA).toInt()
    }

    private fun convertMetresToKilometres(m: Int?): Int? {
        if (m == null) return null
        return m / METRES_IN_KILOMETRE
    }

    private fun convertDegreesToCardinalDirection(degrees: Int?): String? {
        if (degrees == null) return null
        return when(degrees) {
            in 0..11 -> "N"
            in 12..34 -> "NNE"
            in 35..56 -> "NE"
            in 57..79 -> "ENE"
            in 80..101 -> "E"
            in 102..124 -> "ESE"
            in 125..146 -> "SE"
            in 147..169 -> "SSE"
            in 170..191 -> "S"
            in 192..214 -> "SSW"
            in 215..236 -> "SW"
            in 237..259 -> "WSW"
            in 260..281 -> "W"
            in 282..304 -> "WNW"
            in 304..326 -> "NW"
            in 326..349 -> "NNW"
            else -> null
        }
    }

    companion object {
        private const val MMHG_IN_ONE_HPA = 0.75
        private const val METRES_IN_KILOMETRE = 1000

        private const val DATE_TIME_PATTERN = "dd-MM HH:mm"
        private const val ONLY_TIME_PATTERN = "HH:mm"
    }
}