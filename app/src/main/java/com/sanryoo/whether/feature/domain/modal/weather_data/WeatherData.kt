package com.sanryoo.whether.feature.domain.modal.weather_data

import androidx.compose.ui.graphics.Color
import com.sanryoo.whether.R

data class WeatherData(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) {
    companion object{
        fun getBackgroundImage(weatherId:  Int?): Int {
            return when (weatherId) {
                in 200..232 -> R.drawable.thunderstorm
                in 300..321 -> R.drawable.drizzle
                521 -> R.drawable.shower_rain
                in 500..531 -> R.drawable.rain
                in 600..622 -> R.drawable.snow
                in 701..707 -> R.drawable.mist
                800 -> R.drawable.clear_sky
                801 -> R.drawable.few_clouds
                802 -> R.drawable.scattered_clouds
                803 -> R.drawable.broken_clouds
                804 -> R.drawable.orvercast_cloud
                else -> R.drawable.few_clouds
            }
        }

        fun isLightSystemBar(weatherId:  Int?): Boolean {
            return when (weatherId) {
                in 200..232 -> false
                in 300..321 -> false
                521 -> false
                in 500..531 -> false
                in 600..622 -> true
                in 701..707 -> true
                800 -> false
                801 -> true
                802 -> true
                803 -> false
                804 -> false
                else -> false
            }
        }

        fun getBackgroundColor(weatherId:  Int?): Color {
            return when (weatherId) {
                in 200..232 -> Color(0xFF011234).copy(alpha = 0.6f)
                in 300..321 -> Color(0xFF1B2228).copy(alpha = 0.6f)
                521 -> Color(0xFF030D10).copy(alpha = 0.6f)
                in 500..531 -> Color(0xFF25486A).copy(alpha = 0.6f)
                in 600..622 -> Color(0xFFEEF3FD).copy(alpha = 0.6f)
                in 701..707 -> Color(0xFFC7C8CA).copy(alpha = 0.6f)
                800 -> Color(0xFF369CDA).copy(alpha = 0.6f)
                801 -> Color(0xFF2C79C9).copy(alpha = 0.6f)
                802 -> Color(0xFF1462A9).copy(alpha = 0.6f)
                803 -> Color(0xFFB4B7B1).copy(alpha = 0.6f)
                804 -> Color(0xFF151C2E).copy(alpha = 0.6f)
                else -> Color(0xFFFFFFFF).copy(alpha = 0.6f)
            }
        }

        fun getContentColor(weatherId:  Int?): Color {
            return when (weatherId) {
                in 200..232 -> Color.White
                in 300..321 -> Color.White
                521 -> Color.White
                in 500..531 -> Color.White
                in 600..622 -> Color.Black
                in 701..707 -> Color.Black
                800 -> Color.White
                801 -> Color.White
                802 -> Color.White
                803 -> Color.Black
                804 -> Color.White
                else -> Color.Black
            }
        }
    }
}