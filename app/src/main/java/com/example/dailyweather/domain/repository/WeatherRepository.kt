package com.example.dailyweather.domain.repository

import com.example.dailyweather.data.model.ApiResults
import com.example.dailyweather.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeather(city: String): ApiResults<Weather>
}