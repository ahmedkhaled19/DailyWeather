package com.example.dailyweather.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class RecentCityLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : RecentCityLocalDataSource {

    override suspend fun getLastCity(): String? {
        return dataStore.data.first()[LAST_CITY]
    }

    override suspend fun saveLastCity(city: String) {
        dataStore.edit { preferences ->
            preferences[LAST_CITY] = city
        }
    }

    private companion object {
        val LAST_CITY = stringPreferencesKey("last_city")
    }
}