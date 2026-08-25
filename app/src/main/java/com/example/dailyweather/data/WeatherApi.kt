package com.example.dailyweather.data

import com.example.dailyweather.data.model.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("{city}?format=j1")
    suspend fun getWeather(
        @Path("city") city: String
    ): WeatherResponseDto
}