package com.example.dailyweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyweather.data.model.ApiResult
import com.example.dailyweather.domain.usecases.GetLastCityUseCase
import com.example.dailyweather.domain.usecases.GetWeatherUseCase
import com.example.dailyweather.domain.usecases.SaveLastCityUseCase
import com.example.dailyweather.presentation.model.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLastCityUseCase: GetLastCityUseCase,
    private val saveLastCityUseCase: SaveLastCityUseCase
) : ViewModel() {

    private val _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city.asStateFlow()

    init {
        loadLastCity()
    }

    private fun loadLastCity() {
        viewModelScope.launch {
            val lastCity = getLastCityUseCase()
            if (lastCity.isNullOrBlank()) {
                _weatherUiState.value = WeatherUiState.Empty
            } else {
                _city.value = lastCity
                loadWeather(lastCity)
            }
        }
    }

    fun onCityChanged(city: String) {
        _city.value = city
    }

    fun searchWeather() {
        val city = _city.value.trim()
        if (city.isBlank()) {
            _weatherUiState.value = WeatherUiState.Error("Please enter a valid city")
            return
        }
        loadWeather(city)
    }

    private fun loadWeather(city: String) {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading
            when (val result = getWeatherUseCase(city)) {
                is ApiResult.Success -> {
                    saveLastCityUseCase(city)
                    _weatherUiState.value = WeatherUiState.Success(result.data)

                }

                is ApiResult.Failure -> {
                    _weatherUiState.value = WeatherUiState.Error(result.errorMessage)
                }

            }
        }
    }
}