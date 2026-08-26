package com.example.dailyweather.data.mapper

import com.example.dailyweather.data.model.CurrentConditionDto
import com.example.dailyweather.data.model.HourlyWeatherDto
import com.example.dailyweather.data.model.NearestAreaDto
import com.example.dailyweather.data.model.WeatherDayDto
import com.example.dailyweather.data.model.WeatherDescriptionDto
import com.example.dailyweather.data.model.WeatherIconDto
import com.example.dailyweather.data.model.WeatherResponseDto
import com.example.dailyweather.data.model.WeatherValueDto
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherMapperTest {

    private lateinit var mapper: WeatherMapper

    @Before
    fun setup() {
        mapper = WeatherMapper()
    }

    @Test
    fun `map should correctly map WeatherResponseDto to Weather domain model`() {
        val response = WeatherResponseDto(
            current_condition = listOf(
                CurrentConditionDto(
                    temp_C = "25",
                    FeelsLikeC = "27",
                    humidity = "40",
                    windspeedKmph = "15",
                    uvIndex = "5",
                    weatherDesc = listOf(WeatherDescriptionDto("Sunny")),
                    weatherIconUrl = listOf(WeatherIconDto("https://example.com/icon.png"))
                )
            ),
            nearest_area = listOf(
                NearestAreaDto(
                    areaName = listOf(WeatherValueDto("Cairo")),
                    country = listOf(WeatherValueDto("Egypt"))
                )
            ),
            weather = listOf(
                WeatherDayDto(
                    date = "2023-10-27",
                    maxtempC = "30",
                    mintempC = "20",
                    hourly = listOf(
                        HourlyWeatherDto(
                            time = "1200",
                            tempC = "25",
                            chanceofrain = "0",
                            weatherDesc = listOf(WeatherDescriptionDto("Sunny")),
                            weatherIconUrl = listOf(WeatherIconDto("https://example.com/icon.png"))
                        )
                    )
                )
            )
        )

        val result = mapper.map(response)

        assertEquals("Cairo", result.city)
        assertEquals("Egypt", result.country)
        assertEquals(25, result.current.temperatureCelsius)
        assertEquals(27, result.current.feelsLikeCelsius)
        assertEquals(40, result.current.humidity)
        assertEquals(15, result.current.windSpeedKmph)
        assertEquals(5, result.current.uvIndex)
        assertEquals("Sunny", result.current.description)
        assertEquals("https://example.com/icon.png", result.current.iconUrl)
        assertEquals(1, result.forecast.size)
        assertEquals("2023-10-27", result.forecast[0].date)
        assertEquals(20, result.forecast[0].minTemperatureCelsius)
        assertEquals(30, result.forecast[0].maxTemperatureCelsius)
        assertEquals(1, result.forecast[0].hourlyForecast.size)
        assertEquals("1200", result.forecast[0].hourlyForecast[0].time)
        assertEquals(25, result.forecast[0].hourlyForecast[0].temperatureCelsius)
        assertEquals(0, result.forecast[0].hourlyForecast[0].chanceOfRain)
    }

    @Test
    fun `map should return default values when DTO lists are empty`() {
        val response = WeatherResponseDto(
            current_condition = emptyList(),
            nearest_area = emptyList(),
            weather = emptyList()
        )

        val result = mapper.map(response)

        assertEquals("", result.city)
        assertEquals("", result.country)
        assertEquals(0, result.current.temperatureCelsius)
        assertEquals(0, result.current.feelsLikeCelsius)
        assertEquals(0, result.current.humidity)
        assertEquals(0, result.current.windSpeedKmph)
        assertEquals(0, result.current.uvIndex)
        assertEquals("", result.current.description)
        assertEquals("", result.current.iconUrl)
        assertEquals(0, result.forecast.size)
    }
}
