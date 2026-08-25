package com.example.dailyweather.domain.model

data class DailyForecast(
    val date: String,
    val minTemperatureCelsius: Int,
    val maxTemperatureCelsius: Int,
    val hourlyForecast: List<HourlyForecast>
)
