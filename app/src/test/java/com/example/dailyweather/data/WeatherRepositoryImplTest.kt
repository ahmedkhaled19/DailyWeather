package com.example.dailyweather.data

import com.example.dailyweather.data.local.RecentCityLocalDataSource
import com.example.dailyweather.data.mapper.WeatherMapper
import com.example.dailyweather.data.model.ApiResult
import com.example.dailyweather.data.model.WeatherResponseDto
import com.example.dailyweather.data.repository.WeatherRepositoryImpl
import com.example.dailyweather.domain.model.Weather
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class WeatherRepositoryImplTest {

    @MockK
    private lateinit var remoteDataSource: WeatherRemoteDataSource

    @MockK
    private lateinit var localDataSource: RecentCityLocalDataSource

    @MockK
    private lateinit var weatherMapper: WeatherMapper

    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = WeatherRepositoryImpl(remoteDataSource, localDataSource, weatherMapper)
    }

    @Test
    fun `getWeather returns success when remote data source succeeds`() = runTest {
        val city = "Cairo"
        val mockResponse = mockk<WeatherResponseDto>()
        val mockWeather = mockk<Weather>()
        coEvery { remoteDataSource.getWeather(city) } returns mockResponse
        coEvery { weatherMapper.map(mockResponse) } returns mockWeather

        val result = repository.getWeather(city)
      
        assertTrue(result is ApiResult.Success)
        assertEquals(mockWeather, (result as ApiResult.Success).data)
    }

    @Test
    fun `getWeather returns failure when remote data source throws exception`() = runTest {
        val city = "Cairo"
        coEvery { remoteDataSource.getWeather(city) } throws IOException("Network error")
       
        val result = repository.getWeather(city)
      
        assertTrue(result is ApiResult.Failure)
        assertEquals("No internet connection", (result as ApiResult.Failure).errorMessage)
    }

    @Test
    fun `getLastCity returns city from local data source`() = runTest {
        val city = "Cairo"
        coEvery { localDataSource.getLastCity() } returns city
       
        val result = repository.getLastCity()
      
        assertEquals(city, result)
    }

    @Test
    fun `saveLastCity calls local data source`() = runTest {
        val city = "Cairo"
        coEvery { localDataSource.saveLastCity(city) } returns Unit

        repository.saveLastCity(city)

        coVerify { localDataSource.saveLastCity(city) }
    }
}
