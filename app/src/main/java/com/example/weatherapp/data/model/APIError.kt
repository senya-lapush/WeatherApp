package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class APIError(
    @SerializedName("cod")
    val code: Int?,
    @SerializedName("message")
    val message: String?
)