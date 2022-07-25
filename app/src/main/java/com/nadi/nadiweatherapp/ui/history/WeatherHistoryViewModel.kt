package com.nadi.nadiweatherapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadi.data.repository.WeatherRepositoryImplementer
import com.nadi.domain.weather.entity.Weather
import com.nadi.nadiweatherapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val weatherRepositoryImplementer: WeatherRepositoryImplementer
) :
    ViewModel() {
    private val _weather = MutableStateFlow<List<Weather>>(emptyList())
    val weather: StateFlow<List<Weather>> = _weather

    private val _status = MutableSharedFlow<Status>()
    val status: SharedFlow<Status> = _status

    private val _onMessageError = MutableSharedFlow<Exception>()
    val onMessageError: SharedFlow<Exception> = _onMessageError

    fun getWeatherDetails(countryName: String) {
        viewModelScope.launch {
            _weather.emit(weatherRepositoryImplementer.getWeatherHistory(countryName))
        }
    }


}