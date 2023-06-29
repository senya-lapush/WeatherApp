package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("base")
    val base: String?,
    @SerializedName("clouds")
    val clouds: Clouds?,
    @SerializedName("cod")
    val cod: Int?,
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: Sys?,
    @SerializedName("timezone")
    val timezone: Int?,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("wind")
    val wind: Wind
)

data class Clouds(
    @SerializedName("all")
    val all: Int?
)

data class Coord(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)

data class Main(
    @SerializedName("feels_like")
    val feels_like: Double?,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_max")
    val temp_max: Double?,
    @SerializedName("temp_min")
    val temp_min: Double?
)

data class Sys(
    @SerializedName("country")
    val country: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("sunrise")
    val sunrise: Long?,
    @SerializedName("sunset")
    val sunset: Long?,
    @SerializedName("type")
    val type: Int?
)

data class Wind(
    @SerializedName("deg")
    val deg: Int?,
    @SerializedName("speed")
    val speed: Double
)