package com.sanryoo.whether.feature.domain.modal.forecast

import com.sanryoo.whether.feature.domain.modal.weather_data.Clouds
import com.sanryoo.whether.feature.domain.modal.weather_data.Main
import com.sanryoo.whether.feature.domain.modal.weather_data.WeatherData
import com.sanryoo.whether.feature.domain.modal.weather_data.Wind

data class Weather (
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<WeatherData>,
    val wind: Wind
)