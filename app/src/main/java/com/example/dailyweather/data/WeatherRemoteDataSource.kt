package com.example.dailyweather.data

import com.example.dailyweather.data.model.WeatherResponseDto
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherApi: WeatherApi
) {

    suspend fun getWeather(city: String): WeatherResponseDto {
        return weatherApi.getWeather(city)
    }

}