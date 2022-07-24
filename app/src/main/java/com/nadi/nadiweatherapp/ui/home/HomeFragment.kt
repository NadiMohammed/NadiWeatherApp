package com.nadi.nadiweatherapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.hbb20.CountryCodePicker
import com.nadi.domain.weather.entity.City
import com.nadi.domain.weather.entity.Weather
import com.nadi.nadiweatherapp.databinding.FragmentHomeBinding
import com.nadi.nadiweatherapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnCityClickListener {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var citiesAdapter: CitiesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObservers()
        onClick()
    }

    private fun initView() {
        citiesAdapter = CitiesAdapter(this)

        binding.citiesRecyclerView.adapter = citiesAdapter

        binding.countryCodePicker.setOnCountryChangeListener(object :
            CountryCodePicker.OnCountryChangeListener {
            override fun onCountrySelected() {
                homeViewModel.saveCity(City(binding.countryCodePicker.selectedCountryName.toString()))
                homeViewModel.getCities()
            }
        })
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    homeViewModel.cities.collect {
                        citiesAdapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun onClick() {
        binding.addCityFab.setOnClickListener {
            binding.countryCodePicker.launchCountrySelectionDialog()
        }

    }

    override fun onCityItemClick(cityName: String) {
        navigateToDetails(cityName)
    }

    override fun onInfoItemClick(cityName: String) {
        navigateToHistory(cityName)
    }

    private fun navigateToDetails(cityName: String) {
        requireView().findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(cityName))
    }

    private fun navigateToHistory(cityName: String) {
        requireView().findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToWeatherHistoryFragment(cityName))
    }

}