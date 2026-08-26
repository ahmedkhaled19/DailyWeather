package com.example.dailyweather.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyweather.R

@Composable
fun CitySearchSectionContent(
    city: String,
    onCityChanged: (String) -> Unit,
    onSearch: () -> Unit,
    enabled: Boolean = true
) {
    val focusManager = LocalFocusManager.current
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            value = city,
            onValueChange = onCityChanged,
            modifier = Modifier.weight(1f),
            singleLine = true,
            enabled = enabled,
            label = {
                Text(stringResource(R.string.city))
            },
            placeholder = {
                Text(stringResource(R.string.enter_city))
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > 500L) {
                    lastClickTime = currentTime
                    focusManager.clearFocus()
                    onSearch()
                }
            },
            enabled = enabled
        ) {
            Text(stringResource(R.string.search))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CitySearchPreview() {
    MaterialTheme {
        Surface {
            CitySearchSectionContent(
                city = "Alexandria",
                onCityChanged = {},
                onSearch = {}
            )
        }
    }
}
