package com.example.compose_mvvm_weather_app.networking

import com.example.compose_mvvm_weather_app.model.WeatherApiResponse
import com.example.compose_mvvm_weather_app.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkingService {

    @GET("data/2.5/onecall?exclude=minutely,hourly,alerts&lang=en&units=imperial")
    suspend fun fetchWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = Constants.APP_ID
    ): WeatherApiResponse

}