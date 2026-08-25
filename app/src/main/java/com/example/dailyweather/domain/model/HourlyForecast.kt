package com.example.dailyweather.domain.model

data class HourlyForecast(
    val time: String,
    val temperatureCelsius: Int,
    val chanceOfRain: Int,
    val description: String,
    val iconUrl: String
)
