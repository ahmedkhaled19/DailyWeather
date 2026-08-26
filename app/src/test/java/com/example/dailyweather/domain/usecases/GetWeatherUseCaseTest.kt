package com.example.dailyweather.domain.usecases

import com.example.dailyweather.data.model.ApiResult
import com.example.dailyweather.domain.model.Weather
import com.example.dailyweather.domain.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetWeatherUseCaseTest {

    @MockK
    private lateinit var repository: WeatherRepository

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getWeatherUseCase = GetWeatherUseCase(repository)
    }

    @Test
    fun `invoke should return result from repository`() = runTest {
        val city = "Alexandria"
        val expectedResult = ApiResult.Success(mockk<Weather>())
        coEvery { repository.getWeather(city) } returns expectedResult

        val actualResult = getWeatherUseCase(city)

        assertEquals(expectedResult, actualResult)
    }
}
