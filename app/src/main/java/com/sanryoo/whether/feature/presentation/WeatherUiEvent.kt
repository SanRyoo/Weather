package com.sanryoo.whether.feature.presentation

sealed class WeatherUiEvent {
    data class AnimateAlphaBackground(val show: Boolean): WeatherUiEvent()
    data class AnimateScrollWeathers(val index: Int): WeatherUiEvent()
    data class AnimateScrollDates(val index: Int): WeatherUiEvent()
}
