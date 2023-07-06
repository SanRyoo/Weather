package com.sanryoo.whether.feature.domain.repository

import com.sanryoo.whether.feature.data.api.WeatherAPI
import com.sanryoo.whether.feature.data.repository.WeatherRepository
import com.sanryoo.whether.feature.domain.modal.current.CurrentWeather
import com.sanryoo.whether.feature.domain.modal.forecast.Forecast
import retrofit2.Response

class WeatherRepositoryImpl(private val weatherAPI: WeatherAPI): WeatherRepository {

    override suspend fun getCurrentWeather(
        cityName: String,
        units: String,
        appId: String
    ): Response<CurrentWeather> {
        return weatherAPI.getCurrentWeather(cityName, units, appId)
    }

    override suspend fun getForecastNext5Days(
        cityName: String,
        units: String,
        appId: String
    ): Response<Forecast> {
        return weatherAPI.getForecastNext5Days(cityName, units, appId)
    }

}