package com.example.compose_mvvm_weather_app.repository

import com.example.compose_mvvm_weather_app.model.WeatherApiResponse
import com.example.compose_mvvm_weather_app.networking.NetworkingService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val networkingService: NetworkingService) {

    suspend fun fetchWeather(long: String, lat: String): WeatherApiResponse =
        networkingService.fetchWeather(lon = long, lat = lat)

}