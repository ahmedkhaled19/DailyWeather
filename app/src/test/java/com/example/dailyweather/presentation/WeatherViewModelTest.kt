package com.example.dailyweather.presentation

import com.example.dailyweather.data.model.ApiResult
import com.example.dailyweather.domain.model.Weather
import com.example.dailyweather.domain.usecases.GetLastCityUseCase
import com.example.dailyweather.domain.usecases.GetWeatherUseCase
import com.example.dailyweather.domain.usecases.SaveLastCityUseCase
import com.example.dailyweather.presentation.model.WeatherUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @MockK
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @MockK
    private lateinit var getLastCityUseCase: GetLastCityUseCase

    @MockK
    private lateinit var saveLastCityUseCase: SaveLastCityUseCase

    private lateinit var viewModel: WeatherViewModel
    
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        
        coEvery { getLastCityUseCase() } returns null
        
        viewModel = WeatherViewModel(getWeatherUseCase, getLastCityUseCase, saveLastCityUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load last city and fetch weather if exists`() = runTest {
        val city = "Alexandria"
        coEvery { getLastCityUseCase() } returns city
        coEvery { getWeatherUseCase(city) } returns ApiResult.Success(mockk())
        coEvery { saveLastCityUseCase(city) } returns Unit

        val newViewModel = WeatherViewModel(getWeatherUseCase, getLastCityUseCase, saveLastCityUseCase)

        assertEquals(city, newViewModel.city.value)
        assertTrue(newViewModel.weatherUiState.value is WeatherUiState.Success)
    }

    @Test
    fun `searchWeather should update state to Success when use case succeeds`() = runTest {
        val city = "Alexandria"
        val weather = mockk<Weather>()
        viewModel.onCityChanged(city)
        coEvery { getWeatherUseCase(city) } returns ApiResult.Success(weather)
        coEvery { saveLastCityUseCase(city) } returns Unit

        viewModel.searchWeather()

        assertTrue(viewModel.weatherUiState.value is WeatherUiState.Success)
        assertEquals(weather, (viewModel.weatherUiState.value as WeatherUiState.Success).weather)
        coVerify { saveLastCityUseCase(city) }
    }

    @Test
    fun `searchWeather should update state to Error when use case fails`() = runTest {
        val city = "InvalidCity"
        viewModel.onCityChanged(city)
        coEvery { getWeatherUseCase(city) } returns ApiResult.Failure("Error", "City not found")

        viewModel.searchWeather()

        assertTrue(viewModel.weatherUiState.value is WeatherUiState.Error)
        assertEquals("Error", (viewModel.weatherUiState.value as WeatherUiState.Error).message)
    }

    @Test
    fun `onCityChanged should filter non-English characters`() {
        val input = "Alexandria 123 القاهرة"
        viewModel.onCityChanged(input)
        assertEquals("Alexandria  ", viewModel.city.value)
    }

    @Test
    fun `searchWeather should not trigger request if city is same as last searched`() = runTest {
        val city = "Alexandria"
        coEvery { getWeatherUseCase(city) } returns ApiResult.Success(mockk())
        coEvery { saveLastCityUseCase(city) } returns Unit
        
        viewModel.onCityChanged(city)
        viewModel.searchWeather() // First search
        
        assertTrue(viewModel.weatherUiState.value is WeatherUiState.Success)
        
        viewModel.searchWeather() // Second search with same city
        
        coVerify(exactly = 1) { getWeatherUseCase(city) }
    }
}
