package com.sanryoo.whether.feature.domain.modal.current

import com.sanryoo.whether.feature.domain.modal.weather_data.Clouds
import com.sanryoo.whether.feature.domain.modal.weather_data.Coord
import com.sanryoo.whether.feature.domain.modal.weather_data.Main
import com.sanryoo.whether.feature.domain.modal.weather_data.WeatherData
import com.sanryoo.whether.feature.domain.modal.weather_data.Wind

data class CurrentWeather(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherData>,
    val wind: Wind
)