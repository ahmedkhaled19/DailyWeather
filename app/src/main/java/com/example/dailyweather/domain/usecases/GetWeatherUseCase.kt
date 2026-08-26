package com.example.dailyweather.domain.usecases

import com.example.dailyweather.data.model.ApiResult
import com.example.dailyweather.domain.model.Weather
import com.example.dailyweather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(city: String): ApiResult<Weather> {
        return repository.getWeather(city)
    }
}