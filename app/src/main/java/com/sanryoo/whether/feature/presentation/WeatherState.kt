package com.sanryoo.whether.feature.presentation

import androidx.annotation.DrawableRes
import com.sanryoo.whether.feature.domain.modal.weather_data.WeatherData
import com.sanryoo.whether.feature.domain.modal.forecast.Weather
import java.util.Date

data class WeatherState(
    var showWeather: ShowWeather? = null,
    var showWeathers: List<Weather> = emptyList(),
    var dates: List<Date> = emptyList(),
    var currentIndexWeather: Int = 0,
    var currentIndexDate: Int = 0
)

data class ShowWeather(
    var date: Date,
    var weatherData: WeatherData,
    var temp: Double,
    var tempMax: Double,
    var tempMin: Double,
    val visibility: Int,
    val windSpeed: Double
)
