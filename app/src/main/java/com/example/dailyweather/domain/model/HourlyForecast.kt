package com.example.dailyweather.domain.model

import java.util.Locale

data class HourlyForecast(
    val time: String,
    val temperatureCelsius: Int,
    val chanceOfRain: Int,
    val description: String,
    val iconUrl: String
){
    fun getFormattedTime(): String {
        val minutes = time.toIntOrNull() ?: return time
        val hour = minutes / 100
        return String.format(Locale.getDefault(), "%02d:00", hour)
    }
}

