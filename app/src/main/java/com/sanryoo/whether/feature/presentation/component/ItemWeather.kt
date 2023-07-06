package com.sanryoo.whether.feature.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sanryoo.whether.feature.domain.modal.forecast.Weather
import com.sanryoo.whether.feature.util.hourFormat
import com.sanryoo.whether.feature.util.remoteDateFormat

@Composable
fun ItemWeather(
    modifier: Modifier = Modifier,
    weather: Weather,
    isSelected: Boolean,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor)
            .then(
                if (isSelected)
                    Modifier.border(
                        width = 2.dp,
                        color = contentColor,
                        shape = RoundedCornerShape(15.dp)
                    )
                else
                    Modifier
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick
            )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val date = remoteDateFormat.parse(weather.dt_txt)
            date?.let { Text(text = hourFormat.format(it), fontSize = 24.sp) }
            AsyncImage(
                model = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png",
                contentDescription = "Icon Weather",
                modifier = Modifier.size(60.dp)
            )
            Text(text = "${weather.main.temp} ℃")
        }
    }
}