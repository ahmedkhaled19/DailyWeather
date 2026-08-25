package com.example.dailyweather.domain.model

data class CurrentWeather(
    val temperatureCelsius: Int,
    val feelsLikeCelsius: Int,
    val humidity: Int,
    val windSpeedKmph: Int,
    val uvIndex: Int,
    val description: String,
    val iconUrl: String
)
