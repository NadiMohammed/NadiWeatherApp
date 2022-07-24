package com.nadi.data.source.remote.openWeatherMap.weather

import com.nadi.data.source.remote.openWeatherMap.weather.entity.RemoteWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather?")
    suspend fun getWeatherDetails(@Query("q") cityName: String): Response<RemoteWeather>
}