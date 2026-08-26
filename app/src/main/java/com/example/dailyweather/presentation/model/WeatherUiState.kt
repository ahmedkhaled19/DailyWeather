package com.example.dailyweather.presentation.model

import com.example.dailyweather.domain.model.Weather

sealed interface WeatherUiState {

    data object Idle : WeatherUiState

    data object Empty : WeatherUiState

    data object Loading : WeatherUiState

    data class Success(
        val weather: Weather
    ) : WeatherUiState

    data class Error(
        val message: String
    ) : WeatherUiState
}