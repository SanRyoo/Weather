package com.sanryoo.whether.feature.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanryoo.whether.BuildConfig
import com.sanryoo.whether.feature.data.repository.WeatherRepository
import com.sanryoo.whether.feature.domain.modal.forecast.Weather
import com.sanryoo.whether.feature.util.Units
import com.sanryoo.whether.feature.util.getDateCalendar
import com.sanryoo.whether.feature.util.myDateFormat
import com.sanryoo.whether.feature.util.remoteDateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.ParseException
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _allWeather = MutableStateFlow<List<Weather>>(emptyList())

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<WeatherUiEvent>()
    val event = _event.asSharedFlow()

    private var observerCurrentWeatherJob: Job? = null

    init {
        observerCurrentWeather()
        getAllWeather()
    }


    fun observerCurrentWeather() {
        _state.update { it.copy(currentIndexWeather = -1, currentIndexDate = -1) }
        observerCurrentWeatherJob = viewModelScope.launch {
            while (true) {
                Log.d("WeatherViewModel", "Observing current weather...")
                try {
                    val response = weatherRepository.getCurrentWeather(
                        cityName = "Hanoi",
                        units = Units.METRIC,
                        appId = BuildConfig.API_KEY
                    )
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let { currentWeather ->
                            _event.emit(WeatherUiEvent.AnimateAlphaBackground(false))
                            val currentShowWeather = ShowWeather(
                                date = Date(System.currentTimeMillis()),
                                weatherData = currentWeather.weather[0],
                                temp = currentWeather.main.temp,
                                tempMax = currentWeather.main.temp_max,
                                tempMin = currentWeather.main.temp_min,
                                visibility = currentWeather.visibility,
                                windSpeed = currentWeather.wind.speed
                            )

                            _state.update { it.copy(showWeather = currentShowWeather) }
                            _event.emit(WeatherUiEvent.AnimateAlphaBackground(true))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //Recall api when step to a new minute
                val currentSeconds = Calendar.getInstance().apply {
                    time = Date(System.currentTimeMillis())
                }.get(Calendar.SECOND)
                delay((60 - currentSeconds).seconds)
            }
        }
        viewModelScope.launch {
            observerCurrentWeatherJob?.join()
        }
    }

    private fun getAllWeather() {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getForecastNext5Days(
                    cityName = "Hanoi",
                    units = Units.METRIC,
                    appId = BuildConfig.API_KEY
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { forecast ->
                        _allWeather.update { forecast.list }

                        val dates = forecast.list.groupBy {
                            remoteDateFormat.parse(it.dt_txt)?.let { weatherTime ->
                                return@groupBy myDateFormat.parse(myDateFormat.format(weatherTime))
                            }
                        }.keys.mapNotNull { it }

                        _state.update { it.copy(dates = dates) }
                        filterWeathersDay(
                            time = Date(System.currentTimeMillis()),
                            resetShowWeather = false
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@launch
            }
        }
    }

    fun setCurrentIndexWeather(newIndex: Int) {
        _state.update { it.copy(currentIndexWeather = newIndex) }
        viewModelScope.launch {
            _event.emit(WeatherUiEvent.AnimateScrollWeathers(newIndex))
        }
    }

    fun setCurrentIndexDate(newIndex: Int) {
        _state.update { it.copy(currentIndexDate = newIndex, currentIndexWeather = 0) }
        viewModelScope.launch {
            _event.emit(WeatherUiEvent.AnimateScrollDates(newIndex))
            _event.emit(WeatherUiEvent.AnimateScrollWeathers(0))
        }
    }

    fun filterWeathersDay(time: Date, resetShowWeather: Boolean = true) {
        val showWeathers = _allWeather.value.filter { weatherByDay ->
            try {
                remoteDateFormat.parse(weatherByDay.dt_txt)?.let { currentDate ->
                    return@filter time.getDateCalendar() == currentDate.getDateCalendar()
                }
            } catch (e: ParseException) {
                return@filter false
            }
            return@filter false
        }

        _state.update { it.copy(showWeathers = showWeathers) }
        if (resetShowWeather) {
            setShowWeather(showWeathers[0])
        }
    }

    fun setShowWeather(weather: Weather) {
        observerCurrentWeatherJob?.run {
            if (isActive) {
                cancel()
                Log.d("WeatherViewModel", "Stop observe...")
            }
        }
        viewModelScope.launch {
            _event.emit(WeatherUiEvent.AnimateAlphaBackground(false))
            _state.update {
                it.copy(
                    showWeather = ShowWeather(
                        date = remoteDateFormat.parse(weather.dt_txt) ?: Date(),
                        weatherData = weather.weather[0],
                        temp = weather.main.temp,
                        tempMax = weather.main.temp_max,
                        tempMin = weather.main.temp_min,
                        visibility = weather.visibility,
                        windSpeed = weather.wind.speed
                    )
                )
            }
            _event.emit(WeatherUiEvent.AnimateAlphaBackground(true))
        }
    }

}

