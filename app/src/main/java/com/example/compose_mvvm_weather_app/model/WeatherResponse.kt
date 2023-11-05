package com.example.compose_mvvm_weather_app.model

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val weeklyWeather: List<WeeklyWeather>,
)

data class Current(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("weather")
    val todayIcon: List<CurrentIcon>
)

data class CurrentIcon(
    @SerializedName("description")
    val description: String,
)

data class WeeklyWeather(
    @SerializedName("temp")
    val temperature: Temperature,
    @SerializedName("weather")
    val weeklyIcon: ArrayList<WeeklyIcon>
)

data class Temperature(
    @SerializedName("day")
    val day: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("max")
    val max: Double,
    @SerializedName("min")
    val min: Double,
    @SerializedName("morn")
    val morn: Double,
    @SerializedName("night")
    val night: Double
)

data class WeeklyIcon(
    @SerializedName("description")
    val description: String
)