package com.nadi.nadiweatherapp

import com.nadi.domain.Result
import com.nadi.domain.weather.entity.Weather
import com.nadi.domain.weather.repository.WeatherRepository
import com.nadi.domain.weather.usecases.GetWeatherUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class GetWeatherUseCaseTest {

    @Test
    fun `GetWeatherUseCase() with successful invoke from repository then return Weather`() =
        runBlockingTest {
            // Arrange
            val repository = Mockito.mock(WeatherRepository::class.java)
            Mockito.`when`(repository.getWeather("Egypt")).thenReturn(Result.Success(Weather()))

            // Act
            val result = GetWeatherUseCase(repository).invoke("Egypt")

            // Assert
            val expected = Result.Success(Weather())
            assertEquals(expected, result)
        }
}
