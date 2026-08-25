package com.example.dailyweather.data.local

interface RecentCityLocalDataSource {

    suspend fun getLastCity(): String?

    suspend fun saveLastCity(city: String)
}