package com.sanryoo.whether.feature.data.api

import com.sanryoo.whether.feature.domain.modal.current.CurrentWeather
import com.sanryoo.whether.feature.domain.modal.forecast.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): Response<CurrentWeather>

    @GET("/data/2.5/forecast")
    suspend fun getForecastNext5Days(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): Response<Forecast>

}