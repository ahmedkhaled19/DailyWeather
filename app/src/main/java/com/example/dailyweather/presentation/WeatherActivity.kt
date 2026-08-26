package com.example.dailyweather.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyweather.R
import com.example.dailyweather.presentation.model.WeatherUiState
import com.example.dailyweather.presentation.ui.CitySearchSectionContent
import com.example.dailyweather.presentation.ui.WeatherStateContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.weatherUiState.collectAsStateWithLifecycle()
                    val city by viewModel.city.collectAsStateWithLifecycle()
                    WeatherScreen(
                        city = city,
                        uiState = uiState,
                        onCityChanged = viewModel::onCityChanged,
                        onSearch = viewModel::searchWeather
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    city: String,
    uiState: WeatherUiState,
    onCityChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(R.string.daily_weather))
            })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CitySearchSectionContent(
                city = city,
                onCityChanged = onCityChanged,
                onSearch = onSearch
            )
            WeatherStateContent(
                uiState = uiState,
                onRetry = onSearch
            )
        }
    }
}
