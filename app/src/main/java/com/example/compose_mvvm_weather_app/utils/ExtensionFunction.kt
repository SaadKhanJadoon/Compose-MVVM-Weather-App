package com.example.compose_mvvm_weather_app.utils

import com.example.compose_mvvm_weather_app.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

fun todayDate(): String {
    val calendar = Calendar.getInstance().time
    val df = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
    return df.format(calendar)
}

fun fahrenheitToCelsius(temperatureInFahrenheit: Double): Int {
    val celsius = (temperatureInFahrenheit - 32) * 5 / 9
    return celsius.toInt()
}

fun metersToKilometers(meters: Int): Int {
    return meters / 1000
}

fun capitalize(capString: String): String {
    val capBuffer = StringBuffer()
    val capMatcher =
        Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString)
    while (capMatcher.find()) {
        capMatcher.appendReplacement(
            capBuffer,
            (capMatcher.group(1)?.uppercase(Locale.ROOT)) + (capMatcher.group(2)
                ?.lowercase(Locale.ROOT))
        )
    }
    return capMatcher.appendTail(capBuffer).toString()
}

fun getWeatherIcon(weather: String): Int {
    return when (weather) {
        "clear sky" -> R.drawable.clear_sky
        "few clouds" -> R.drawable.few_clouds
        "shower rain" -> R.drawable.shower_rain
        "thunderstorm" -> R.drawable.thunderstorm
        "broken clouds" -> R.drawable.broken_clouds
        "overcast clouds" -> R.drawable.scattered_clouds
        "scattered clouds" -> R.drawable.scattered_clouds
        "rain" -> R.drawable.rain
        "snow" -> R.drawable.snow
        "mist" -> R.drawable.mist
        else -> R.drawable.few_clouds
    }
}