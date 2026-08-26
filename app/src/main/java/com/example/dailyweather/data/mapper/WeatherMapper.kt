package com.example.dailyweather.data.mapper

import com.example.dailyweather.data.model.CurrentConditionDto
import com.example.dailyweather.data.model.HourlyWeatherDto
import com.example.dailyweather.data.model.WeatherDayDto
import com.example.dailyweather.data.model.WeatherResponseDto
import com.example.dailyweather.domain.model.CurrentWeather
import com.example.dailyweather.domain.model.DailyForecast
import com.example.dailyweather.domain.model.HourlyForecast
import com.example.dailyweather.domain.model.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun map(response: WeatherResponseDto): Weather {
        val currentCondition = response.current_condition.firstOrNull()
        val nearestArea = response.nearest_area.firstOrNull()
        return Weather(
            city = nearestArea?.areaName?.firstOrNull()?.value.orEmpty(),
            country = nearestArea?.country?.firstOrNull()?.value.orEmpty(),
            current = currentCondition?.toDomain() ?: CurrentWeather(
                temperatureCelsius = 0,
                feelsLikeCelsius = 0,
                humidity = 0,
                windSpeedKmph = 0,
                uvIndex = 0,
                description = "",
                iconUrl = ""
            ),
            forecast = response.weather.map { it.toDomain() }
        )
    }

    private fun CurrentConditionDto.toDomain(): CurrentWeather {
        return CurrentWeather(
            temperatureCelsius = temp_C.toIntOrZero(),
            feelsLikeCelsius = FeelsLikeC.toIntOrZero(),
            humidity = humidity.toIntOrZero(),
            windSpeedKmph = windspeedKmph.toIntOrZero(),
            uvIndex = uvIndex.toIntOrZero(),
            description = weatherDesc.firstOrNull()?.value?.trim().orEmpty(),
            iconUrl = weatherIconUrl.firstOrNull()?.value.orEmpty()
        )
    }

    private fun WeatherDayDto.toDomain(): DailyForecast {
        return DailyForecast(
            date = date,
            minTemperatureCelsius = mintempC.toIntOrZero(),
            maxTemperatureCelsius = maxtempC.toIntOrZero(),
            hourlyForecast = hourly.map { it.toDomain() }
        )
    }

    private fun HourlyWeatherDto.toDomain(): HourlyForecast {
        return HourlyForecast(
            time = time,
            temperatureCelsius = tempC.toIntOrZero(),
            chanceOfRain = chanceofrain.toIntOrZero(),
            description = weatherDesc.firstOrNull()?.value?.trim().orEmpty(),
            iconUrl = weatherIconUrl.firstOrNull()?.value.orEmpty()
        )
    }

    private fun String.toIntOrZero(): Int = toIntOrNull() ?: 0
}