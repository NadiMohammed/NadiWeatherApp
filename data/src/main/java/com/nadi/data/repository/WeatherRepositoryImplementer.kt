package com.nadi.data.repository

import com.nadi.data.CallbackHandler
import com.nadi.data.source.local.database.MainDatabase
import com.nadi.data.source.local.database.weather.entity.asDatabaseModel
import com.nadi.data.source.local.database.weather.entity.asDomainCity
import com.nadi.data.source.local.database.weather.entity.asDomainWeather
import com.nadi.data.source.remote.openWeatherMap.weather.WeatherAPI
import com.nadi.data.source.remote.openWeatherMap.weather.entity.asDomainWeather
import com.nadi.domain.Result
import com.nadi.domain.weather.entity.City
import com.nadi.domain.weather.entity.Weather
import com.nadi.domain.weather.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImplementer @Inject constructor(
    private val api: WeatherAPI,
    private val database: MainDatabase,
) : WeatherRepository, CallbackHandler() {

    override suspend fun insertCity(city: City) {
        database.weatherDAO.insertCity(city.asDatabaseModel())
    }

    override suspend fun getCities(): List<City> {
        return database.weatherDAO.getCities().asDomainCity()
    }

    override suspend fun insertWeather(weather: Weather) {
        database.weatherDAO.insertWeather(weather.asDatabaseModel())
    }

    override suspend fun getWeather(cityName: String): Result<Weather> {
        return when (val result = safeApiCall { api.getWeatherDetails(cityName) }) {
            is Result.Success -> Result.Success(result.results.asDomainWeather())
            is Result.Failed -> result
        }
    }

    override suspend fun getWeatherHistory(cityName: String): List<Weather> =
        database.weatherDAO.getWeatherHistory(cityName).asDomainWeather()
}