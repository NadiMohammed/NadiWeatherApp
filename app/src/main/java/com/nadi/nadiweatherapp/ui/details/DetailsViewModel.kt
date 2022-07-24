package com.nadi.nadiweatherapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadi.data.repository.WeatherRepositoryImplementer
import com.nadi.data.source.local.database.weather.entity.DatabaseWeather
import com.nadi.domain.Result
import com.nadi.domain.weather.entity.Weather
import com.nadi.domain.weather.usecases.GetWeatherUseCase
import com.nadi.nadiweatherapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val weatherRepositoryImplementer: WeatherRepositoryImplementer,
) :
    ViewModel() {

    private val _weatherDetails = MutableStateFlow(Weather())
    val weatherDetails: StateFlow<Weather> = _weatherDetails

    private val _status = MutableSharedFlow<Status>()
    val status: SharedFlow<Status> = _status

    private val _onMessageError = MutableSharedFlow<Exception>()
    val onMessageError: SharedFlow<Exception> = _onMessageError

    fun getWeatherDetails(cityName: String) {
        viewModelScope.launch {
            _status.emit(Status.LOADING)

            try {
                when (val result = getWeatherUseCase(cityName)) {
                    is Result.Success -> {
                        _weatherDetails.emit(result.results)

                        _status.emit(Status.SUCCESS)
                    }

                    is Result.Failed -> {
                        _onMessageError.emit(result.exception!!)
                        _status.emit(Status.ERROR)
                    }
                }

            } catch (e: Exception) {
                _onMessageError.emit(e)
            }

        }
    }

    fun saveWeather(weather: Weather) {
        viewModelScope.launch {
            weatherRepositoryImplementer.insertWeather(weather)
        }
    }


}