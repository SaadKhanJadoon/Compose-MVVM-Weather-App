package com.example.compose_mvvm_weather_app.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_mvvm_weather_app.R
import com.example.compose_mvvm_weather_app.model.TodayWeatherIcon
import com.example.compose_mvvm_weather_app.model.WeatherForWeekItem
import com.example.compose_mvvm_weather_app.model.WeatherUiModel
import com.example.compose_mvvm_weather_app.model.WeeklyWeatherIcon
import com.example.compose_mvvm_weather_app.repository.WeatherRepository
import com.example.compose_mvvm_weather_app.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    @ApplicationContext private val applicationContext: Context,
) : ViewModel() {

    private var city: String = "Islamabad, Pakistan"
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.fetchWeather(long = ISLAMABAD_LONG, lat = ISLAMABAD_LAT)
                var dateCounter = -1
                _uiState.value = WeatherUiState.Loaded(
                    WeatherUiModel(
                        city = city,
                        weather = "${fahrenheitToCelsius(response.current.temp)}",
                        feelsLike = "${fahrenheitToCelsius(response.current.feelsLike)}°C",
                        visibility = "${metersToKilometers(response.current.visibility)} km",
                        uvRadiations = "${response.current.uvi}",
                        humidity = "${response.current.humidity}%",
                        windSpeed = "${response.current.windSpeed} m/s",
                        pressure = "${response.current.pressure} hPa",
                        todayWeatherIcon = response.current.todayIcon.map {
                            TodayWeatherIcon(
                                description = it.description
                            )
                        },
                        forecastForWeek = response.weeklyWeather.drop(1).map { it ->
                            dateCounter++
                            WeatherForWeekItem(
                                day = Calendar.getInstance()
                                    .also { cal -> cal.add(Calendar.DATE, dateCounter) }
                                    .getDisplayName(
                                        Calendar.DAY_OF_WEEK,
                                        Calendar.LONG,
                                        Locale.getDefault()
                                    ).orEmpty(),
                                dayTemp = "${fahrenheitToCelsius(it.temperature.day)}°C",
                                nightTemp = "${fahrenheitToCelsius(it.temperature.night)}°C",
                                weeklyWeatherIcon = it.weeklyIcon.map {
                                    WeeklyWeatherIcon(
                                        description = it.description
                                    )
                                },
                            )
                        }
                    )
                )
            } catch (ex: Exception) {
                if (ex is HttpException && ex.code() == 429) {
                    onQueryLimitReached()
                } else {
                    onErrorOccurred()
                }
            }
        }
    }

    private fun onQueryLimitReached() {
        _uiState.value = WeatherUiState.Error(
            applicationContext.getString(R.string.query_limit_reached)
        )
    }

    private fun onErrorOccurred() {
        _uiState.value = WeatherUiState.Error(
            applicationContext.getString(R.string.something_went_wrong)
        )
    }

    sealed class WeatherUiState {
        object Empty : WeatherUiState()
        object Loading : WeatherUiState()
        class Loaded(val data: WeatherUiModel) : WeatherUiState()
        class Error(val message: String) : WeatherUiState()
    }

    companion object {
        const val ISLAMABAD_LAT = "33.720001"
        const val ISLAMABAD_LONG = "73.059998"
    }
}