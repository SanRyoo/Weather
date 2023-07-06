package com.sanryoo.whether.feature.data.repository

import com.sanryoo.whether.feature.domain.modal.current.CurrentWeather
import com.sanryoo.whether.feature.domain.modal.forecast.Forecast
import retrofit2.Response

interface WeatherRepository {

    suspend fun getCurrentWeather(cityName: String, units: String, appId: String): Response<CurrentWeather>

    suspend fun getForecastNext5Days(cityName: String, units: String, appId: String): Response<Forecast>

}