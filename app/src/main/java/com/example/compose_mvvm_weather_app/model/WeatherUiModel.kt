package com.example.compose_mvvm_weather_app.model

data class WeatherUiModel(
    var city: String = "",
    var weather: String = "",
    var feelsLike: String = "",
    var visibility: String = "",
    var humidity: String = "",
    var uvRadiations: String = "",
    var windSpeed: String = "",
    var pressure: String = "",
    var todayWeatherIcon: List<TodayWeatherIcon> = listOf(),
    var forecastForWeek: List<WeatherForWeekItem> = listOf(),
)

data class WeatherForWeekItem(
    val day: String,
    val dayTemp: String,
    val nightTemp: String,
    var weeklyWeatherIcon: List<WeeklyWeatherIcon>
)

data class TodayWeatherIcon(val description: String)
data class WeeklyWeatherIcon(val description: String)