package com.example.dailyweather.domain.usecases

import com.example.dailyweather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLastCityUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(): String? = repository.getLastCity()

}