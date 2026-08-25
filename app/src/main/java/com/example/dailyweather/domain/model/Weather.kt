package com.example.dailyweather.domain.model

data class Weather(
    val city: String,
    val country: String,
    val current: CurrentWeather,
    val forecast: List<DailyForecast>
)