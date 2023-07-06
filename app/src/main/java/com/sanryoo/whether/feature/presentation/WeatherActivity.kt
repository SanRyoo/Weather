package com.sanryoo.whether.feature.presentation

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanryoo.whether.feature.domain.modal.weather_data.WeatherData
import com.sanryoo.whether.feature.presentation.component.ItemDate
import com.sanryoo.whether.feature.presentation.component.ItemWeather
import com.sanryoo.whether.feature.presentation.component.ShowWeather
import com.sanryoo.whether.feature.presentation.ui.theme.DefaultBackground
import com.sanryoo.whether.feature.presentation.ui.theme.WhetherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : ComponentActivity() {

    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        setContent {
            WhetherTheme {
                val listStateWeathers = rememberLazyListState()
                val listStateDates = rememberLazyListState()

                val state = viewModel.state.collectAsStateWithLifecycle().value

                val backgroundImage = WeatherData.getBackgroundImage(state.showWeather?.weatherData?.id)
                val backgroundColor = WeatherData.getBackgroundColor(state.showWeather?.weatherData?.id)
                val contentColor = WeatherData.getContentColor(state.showWeather?.weatherData?.id)
                val lightSystemBar = WeatherData.isLightSystemBar(state.showWeather?.weatherData?.id)

                //For animate alpha of background when change show weather
                var showImageBackground by remember {
                    mutableStateOf(false)
                }
                val transitionAlpha by animateFloatAsState(
                    targetValue = if (!showImageBackground) 0f else 1f,
                    animationSpec = tween(durationMillis = 1000)
                )
                LaunchedEffect(Unit) {
                    viewModel.event.collect { event ->
                        when (event) {
                            is WeatherUiEvent.AnimateScrollWeathers -> {
                                listStateWeathers.animateScrollToItem(event.index, -400)
                            }

                            is WeatherUiEvent.AnimateScrollDates -> {
                                listStateDates.animateScrollToItem(event.index, -400)
                            }

                            is WeatherUiEvent.AnimateAlphaBackground -> {
                                showImageBackground = event.show
                            }
                        }
                    }
                }
                //Set light system bar depend on background image
                val view = LocalView.current
                if (!view.isInEditMode) {
                    LaunchedEffect(state.showWeather) {
                        val window = (view.context as Activity).window
                        val insetController = WindowCompat.getInsetsController(window, view)

                        insetController.isAppearanceLightStatusBars = lightSystemBar
                        insetController.isAppearanceLightNavigationBars = lightSystemBar
                    }
                }
                Surface(
                    color = backgroundColor,
                    contentColor = contentColor
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DefaultBackground)
                    ) {
                        Image(
                            painter = painterResource(id = backgroundImage),
                            contentDescription = "Background image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(transitionAlpha)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding()
                                .navigationBarsPadding(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                state.showWeather?.let {
                                    ShowWeather(
                                        weather = it,
                                        backgroundColor = backgroundColor
                                    )
                                }
                            }
                            state.showWeather?.let {
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 10.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(backgroundColor)
                                        .clickable { viewModel.observerCurrentWeather() }
                                ) {
                                    Text(
                                        text = "Now",
                                        modifier = Modifier.padding(
                                            vertical = 10.dp,
                                            horizontal = 20.dp
                                        )
                                    )
                                }
                            }
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                state = listStateWeathers
                            ) {
                                itemsIndexed(state.showWeathers) { index, weather ->
                                    ItemWeather(
                                        weather = weather,
                                        isSelected = state.currentIndexWeather == index,
                                        backgroundColor = backgroundColor,
                                        contentColor = contentColor,
                                        onClick = {
                                            viewModel.setShowWeather(weather)
                                            viewModel.setCurrentIndexWeather(index)
                                        }
                                    )
                                }
                            }
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                state = listStateDates
                            ) {
                                itemsIndexed(state.dates) { index, date ->
                                    ItemDate(
                                        modifier = Modifier.fillMaxHeight(),
                                        date = date,
                                        isSelected = state.currentIndexDate == index,
                                        backgroundColor = backgroundColor,
                                        contentColor = contentColor,
                                        onClick = {
                                            viewModel.filterWeathersDay(date)
                                            viewModel.setCurrentIndexDate(index)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}