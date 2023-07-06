package com.sanryoo.whether.feature.domain.modal.forecast

import com.sanryoo.whether.feature.domain.modal.weather_data.Coord

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)