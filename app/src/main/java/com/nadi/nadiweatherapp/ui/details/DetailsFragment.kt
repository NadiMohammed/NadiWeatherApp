package com.nadi.nadiweatherapp.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.nadi.domain.weather.entity.Weather
import com.nadi.nadiweatherapp.databinding.FragmentDetailsBinding
import com.nadi.nadiweatherapp.utils.BaseFragment
import com.nadi.nadiweatherapp.utils.Status
import com.nadi.nadiweatherapp.utils.exceptionsHandler
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun getViewBinding() = FragmentDetailsBinding.inflate(layoutInflater)

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObservers()
        onClick()
    }

    private fun init() {
        detailsViewModel.getWeatherDetails(args.cityName)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    detailsViewModel.weatherDetails.collect {
                        binding.apply {
                            cityNameTxt.text = "${args.cityName}, ${it.countryCode}"
                            tempValueTxt.text = convertKelvinToCelsius(it.temperature).toString()
                            humidityValueTxt.text = "${it.humidity}%"
                            windValueTxt.text = "${it.windspeed} km/h"
                            weatherInfoTxt.text =
                                "RemoteWeather information for ${args.cityName} received on"
                            currentDateTxt.text = currentTimeAndDate()

                            descriptionValueTxt.text = it.description
                            val iconUrl =
                                "https://openweathermap.org/img/w/" + it.icon + ".png"
                            Picasso.get().load(iconUrl).into(binding.weatherImg)

                            if (it.countryCode.isNotEmpty()) {
                                saveWeather(
                                    Weather(
                                        cityName = args.cityName,
                                        dateTime = currentTimeAndDate(),
                                        description = it.description,
                                        temperature = convertKelvinToCelsius(it.temperature),
                                        icon = iconUrl,
                                    )
                                )
                            }

                        }
                    }
                }

                launch {
                    detailsViewModel.onMessageError.collect {
                        exceptionsHandler(requireContext(), it)
                    }
                }

                launch {
                    detailsViewModel.status.collect {
                        when (it) {
                            Status.LOADING -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            Status.SUCCESS -> {
                                binding.progressBar.visibility = View.GONE
                            }
                            Status.ERROR -> {
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onClick() {
        binding.backImg.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    private fun saveWeather(weather: Weather) {
        detailsViewModel.saveWeather(weather)
    }

    private fun currentTimeAndDate(): String {
        val currentTime = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return currentTime.format(timeFormatter)
    }

    private fun convertKelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

}