package com.example.dailyweather.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dailyweather.R
import com.example.dailyweather.domain.model.CurrentWeather
import com.example.dailyweather.domain.model.DailyForecast
import com.example.dailyweather.domain.model.HourlyForecast
import com.example.dailyweather.domain.model.Weather

@Composable
fun WeatherContent(
    weather: Weather
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CurrentWeatherCard(weather)
        }
        item {
            Text(
                text = stringResource(R.string.forecast),
                style = MaterialTheme.typography.titleLarge
            )
        }
        items(weather.forecast) { forecast ->
            ForecastCard(
                forecast = forecast
            )
        }
    }
}

@Composable
private fun CurrentWeatherCard(
    weather: Weather
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "${weather.city}, ${weather.country}",
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(
                            R.string.temperature_c,
                            weather.current.temperatureCelsius
                        ),
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = weather.current.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(
                            R.string.feels_like_c,
                            weather.current.feelsLikeCelsius
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                AsyncImage(
                    model = weather.current.iconUrl,
                    contentDescription = weather.current.description,
                    modifier = Modifier.height(80.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            WeatherDetailsRow(
                label = stringResource(R.string.humidity),
                value = "${weather.current.humidity}%"
            )
            WeatherDetailsRow(
                label = stringResource(R.string.wind),
                value = stringResource(R.string.km_h, weather.current.windSpeedKmph)
            )
            WeatherDetailsRow(
                label = stringResource(R.string.uv_index),
                value = weather.current.uvIndex.toString()
            )
        }
    }
}

@Composable
private fun WeatherDetailsRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ForecastCard(
    forecast: DailyForecast
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = forecast.date,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "${forecast.minTemperatureCelsius}° / " +
                            "${forecast.maxTemperatureCelsius}°",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = stringResource(R.string.hourly_forecast),
                style = MaterialTheme.typography.titleSmall
            )

            forecast.hourlyForecast.forEach { hourly ->
                HourlyForecastItem(forecast = hourly)
            }
        }
    }
}

@Composable
private fun HourlyForecastItem(
    forecast: HourlyForecast
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = forecast.iconUrl,
                contentDescription = forecast.description,
                modifier = Modifier.height(40.dp)
            )

            Column {
                Text(
                    text = forecast.getFormattedTime(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = forecast.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Column {
            Text(
                text =
                stringResource(R.string.temperature_c, forecast.temperatureCelsius),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = stringResource(R.string.rain, forecast.chanceOfRain),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherContentPreview() {
    MaterialTheme {
        Surface {
            WeatherContent(
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
                    forecast = listOf(
                        DailyForecast(
                            date = "2023-10-27",
                            minTemperatureCelsius = 20,
                            maxTemperatureCelsius = 30,
                            hourlyForecast = listOf(
                                HourlyForecast(
                                    time = "1200",
                                    temperatureCelsius = 25,
                                    chanceOfRain = 0,
                                    description = "Sunny",
                                    iconUrl = "https://example.com/icon.png"
                                )
                            )
                        )
                    )
                )
            )
        }
    }
}
