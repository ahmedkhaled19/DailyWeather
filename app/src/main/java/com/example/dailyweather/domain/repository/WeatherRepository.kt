package com.example.dailyweather.domain.repository

import com.example.dailyweather.data.model.ApiResult
import com.example.dailyweather.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeather(city: String): ApiResult<Weather>

    suspend fun getLastCity(): String?

    suspend fun saveLastCity(city: String)
}