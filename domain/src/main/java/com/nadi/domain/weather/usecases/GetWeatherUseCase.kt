package com.nadi.domain.weather.usecases

import com.nadi.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(cityName: String) = weatherRepository.getWeather(cityName)
}
