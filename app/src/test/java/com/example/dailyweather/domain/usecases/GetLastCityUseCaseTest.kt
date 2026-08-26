package com.example.dailyweather.domain.usecases

import com.example.dailyweather.domain.repository.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetLastCityUseCaseTest {

    @MockK
    private lateinit var repository: WeatherRepository

    private lateinit var getLastCityUseCase: GetLastCityUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getLastCityUseCase = GetLastCityUseCase(repository)
    }

    @Test
    fun `invoke should return city from repository`() = runTest {
        val expectedCity = "Alexandria"
        coEvery { repository.getLastCity() } returns expectedCity

        val actualCity = getLastCityUseCase()

        assertEquals(expectedCity, actualCity)
    }
}
