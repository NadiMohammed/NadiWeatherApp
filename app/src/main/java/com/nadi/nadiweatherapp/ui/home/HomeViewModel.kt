package com.nadi.nadiweatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadi.data.repository.WeatherRepositoryImplementer
import com.nadi.domain.weather.entity.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherRepositoryImplementer: WeatherRepositoryImplementer) :
    ViewModel() {

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities

    init {
        getCities()
    }

    fun saveCity(city: City) {
        viewModelScope.launch {
            weatherRepositoryImplementer.insertCity(city)
        }
    }

    fun getCities() {
        viewModelScope.launch {
            _cities.emit(weatherRepositoryImplementer.getCities())
        }
    }

}