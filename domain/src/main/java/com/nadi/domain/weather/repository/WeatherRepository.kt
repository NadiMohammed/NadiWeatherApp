package com.nadi.domain.weather.repository

import com.nadi.domain.Result
import com.nadi.domain.weather.entity.City
import com.nadi.domain.weather.entity.Weather

interface WeatherRepository {

    suspend fun insertCity(city: City)

    suspend fun getCities(): List<City>

    suspend fun insertWeather(weather: Weather)

    suspend fun getWeather(cityName: String): Result<Weather>

    suspend fun getWeatherHistory(cityName: String): List<Weather>
}