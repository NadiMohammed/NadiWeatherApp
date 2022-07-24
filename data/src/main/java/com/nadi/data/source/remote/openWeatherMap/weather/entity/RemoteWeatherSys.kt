package com.nadi.data.source.remote.openWeatherMap.weather.entity

data class RemoteWeatherSys(
    val country: String = "",
    val id: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val type: Int = 0,
)