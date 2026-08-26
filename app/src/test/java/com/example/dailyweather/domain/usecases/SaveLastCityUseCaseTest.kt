package com.example.dailyweather.domain.usecases

import com.example.dailyweather.domain.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveLastCityUseCaseTest {

    @MockK
    private lateinit var repository: WeatherRepository

    private lateinit var saveLastCityUseCase: SaveLastCityUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        saveLastCityUseCase = SaveLastCityUseCase(repository)
    }

    @Test
    fun `invoke should call saveLastCity on repository`() = runTest {
        val city = "Alexandria"
        coEvery { repository.saveLastCity(city) } returns Unit

        saveLastCityUseCase(city)

        coVerify { repository.saveLastCity(city) }
    }
}
