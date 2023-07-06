package com.sanryoo.whether.feature.domain.modal.current

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)