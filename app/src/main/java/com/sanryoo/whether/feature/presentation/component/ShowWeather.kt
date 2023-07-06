package com.sanryoo.whether.feature.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sanryoo.whether.feature.presentation.ShowWeather
import com.sanryoo.whether.feature.util.hourAndDayFormat

@Composable
fun ShowWeather(
    modifier: Modifier = Modifier,
    weather: ShowWeather,
    backgroundColor: Color
) {
    Column(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(20.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hanoi",
            fontSize = 34.sp
        )
        Text(
            text = hourAndDayFormat.format(weather.date),
            fontSize = 34.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = weather.weatherData.main,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            AsyncImage(
                model = "https://openweathermap.org/img/wn/${weather.weatherData.icon}@2x.png",
                contentDescription = "Icon Weather",
                modifier = Modifier.size(60.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${weather.temp} ℃",
            fontSize = 48.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(text = "Min: ${weather.tempMin} ℃", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Max: ${weather.tempMax} ℃", fontSize = 20.sp)
        }
        Text(
            text = "Visibility: ${String.format("%.2f", weather.visibility / 1000f)} km",
            fontSize = 20.sp
        )
        Text(
            text = "Wind speed: ${String.format("%.2f", weather.windSpeed)} m/s",
            fontSize = 20.sp
        )
    }
}