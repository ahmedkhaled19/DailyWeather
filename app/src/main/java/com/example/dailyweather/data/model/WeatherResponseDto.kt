package com.example.dailyweather.data.model

data class WeatherResponseDto(
    val current_condition: List<CurrentConditionDto>,
    val nearest_area: List<NearestAreaDto>,
    val weather: List<WeatherDayDto>
)

data class CurrentConditionDto(
    val temp_C: String,
    val FeelsLikeC: String,
    val humidity: String,
    val windspeedKmph: String,
    val uvIndex: String,
    val weatherDesc: List<WeatherDescriptionDto>,
    val weatherIconUrl: List<WeatherIconDto>
)

data class NearestAreaDto(
    val areaName: List<WeatherValueDto>,
    val country: List<WeatherValueDto>
)

data class WeatherDayDto(
    val date: String,
    val maxtempC: String,
    val mintempC: String,
    val hourly: List<HourlyWeatherDto>
)

data class HourlyWeatherDto(
    val time: String,
    val tempC: String,
    val chanceofrain: String,
    val weatherDesc: List<WeatherDescriptionDto>,
    val weatherIconUrl: List<WeatherIconDto>
)

data class WeatherDescriptionDto(val value: String)

data class WeatherIconDto(val value: String)

data class WeatherValueDto(val value: String)