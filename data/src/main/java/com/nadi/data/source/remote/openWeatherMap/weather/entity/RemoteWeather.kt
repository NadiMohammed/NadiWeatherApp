package com.nadi.data.source.remote.openWeatherMap.weather.entity

import com.nadi.domain.weather.entity.Weather
import java.text.SimpleDateFormat
import java.util.*

data class RemoteWeather(
    val main: RemoteWeatherMain = RemoteWeatherMain(),
    val name: String = "",
    val sys: RemoteWeatherSys = RemoteWeatherSys(),
    val weather: List<RemoteWeatherX> = listOf(),
    val wind: RemoteWeatherWind = RemoteWeatherWind(),
)

fun getCurrentDate(): String =
    SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date())


fun RemoteWeather.asDomainWeather() = Weather(
    cityName = name,
    countryCode = sys.country,
    dateTime = getCurrentDate(),
    description = weather[0].description,
    temperature = main.temp,
    humidity = main.humidity,
    windspeed= wind.speed,
    icon = weather[0].icon
)