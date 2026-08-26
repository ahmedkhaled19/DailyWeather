package com.example.dailyweather.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dailyweather.data.local.RecentCityLocalDataSource
import com.example.dailyweather.data.local.RecentCityLocalDataSourceImpl
import com.example.dailyweather.data.repository.WeatherRepositoryImpl
import com.example.dailyweather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "weather_prefs")

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindRecentCityLocalDataSource(
        recentCityLocalDataSourceImpl: RecentCityLocalDataSourceImpl
    ): RecentCityLocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return context.dataStore
        }
    }
}
