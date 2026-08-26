package com.example.dailyweather.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyweather.R
import com.example.dailyweather.domain.model.CurrentWeather
import com.example.dailyweather.domain.model.Weather
import com.example.dailyweather.presentation.model.WeatherUiState

@Composable
fun WeatherStateContent(
    uiState: WeatherUiState,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            WeatherUiState.Empty -> {
                EmptyWeatherContent()
            }

            WeatherUiState.Loading -> {
                CircularProgressIndicator()
            }

            is WeatherUiState.Error -> {
                ErrorWeatherContent(
                    message = uiState.message,
                    onRetry = onRetry
                )
            }

            is WeatherUiState.Success -> {
                WeatherContent(weather = uiState.weather)
            }

            WeatherUiState.Idle -> {
                // Initial state
            }
        }
    }
}

@Composable
private fun EmptyWeatherContent() {
    Text(
        text = stringResource(R.string.search_for_a_city_to_see_the_weather),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun ErrorWeatherContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    MaterialTheme {
        Surface {
            WeatherStateContent(uiState = WeatherUiState.Loading, onRetry = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    MaterialTheme {
        Surface {
            WeatherStateContent(
                uiState = WeatherUiState.Error("An unexpected error occurred"),
                onRetry = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessPreview() {
    MaterialTheme {
        Surface {
            WeatherStateContent(
                uiState = WeatherUiState.Success(
                    weather = Weather(
                        city = "Cairo",
                        country = "Egypt",
                        current = CurrentWeather(
                            temperatureCelsius = 25,
                            feelsLikeCelsius = 27,
                            humidity = 40,
                            windSpeedKmph = 15,
                            uvIndex = 5,
                            description = "Sunny",
                            iconUrl = "https://example.com/icon.png"
                        ),
                        forecast = emptyList()
                    )
                ),
                onRetry = {}
            )
        }
    }
}
