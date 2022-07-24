package com.nadi.domain.weather.entity

data class Weather(
    var cityName: String = "",
    var countryCode: String = "",
    var dateTime: String = "",
    var description: String = "",
    var temperature: Double = 0.0,
    val humidity: Int = 0,
    var windspeed: Double = 0.0,
    var icon: String = ""
)