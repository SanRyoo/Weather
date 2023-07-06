package com.sanryoo.whether.feature.domain.modal.forecast

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Weather>,
    val message: Int
)