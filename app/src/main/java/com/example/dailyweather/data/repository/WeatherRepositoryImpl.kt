package com.example.dailyweather.data.repository

import com.example.dailyweather.data.WeatherRemoteDataSource
import com.example.dailyweather.data.local.RecentCityLocalDataSource
import com.example.dailyweather.data.mapper.WeatherMapper
import com.example.dailyweather.data.model.ApiResult
import com.example.dailyweather.domain.model.Weather
import com.example.dailyweather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: RecentCityLocalDataSource,
    private val mapper: WeatherMapper
) : WeatherRepository {

    override suspend fun getWeather(city: String): ApiResult<Weather> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getWeather(city)
                val weather = mapper.map(response)
                ApiResult.Success(weather)
            } catch (exception: IOException) {
                ApiResult.Failure(
                    "No internet connection",
                    exception.localizedMessage ?: "Unable to connect to the server"
                )
            } catch (exception: HttpException) {
                ApiResult.Failure(
                    "Request failed",
                    exception.localizedMessage ?: "Server returned an error"
                )

            } catch (exception: Exception) {
                ApiResult.Failure(
                    "Something went wrong",
                    exception.localizedMessage ?: "Unknown error"
                )
            }
        }
    }

    override suspend fun getLastCity(): String? {
        return localDataSource.getLastCity()
    }

    override suspend fun saveLastCity(city: String) {
        localDataSource.saveLastCity(city)
    }
}