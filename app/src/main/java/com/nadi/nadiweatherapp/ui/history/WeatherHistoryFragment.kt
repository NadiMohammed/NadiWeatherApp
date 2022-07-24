package com.nadi.nadiweatherapp.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.nadi.nadiweatherapp.databinding.FragmentWeatherHistoryBinding
import com.nadi.nadiweatherapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherHistoryFragment : BaseFragment<FragmentWeatherHistoryBinding>() {

    private val weatherHistoryViewModel: WeatherHistoryViewModel by viewModels()

    override fun getViewBinding() = FragmentWeatherHistoryBinding.inflate(layoutInflater)

    private val args: WeatherHistoryFragmentArgs by navArgs()

    private lateinit var historyAdapter: HistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initView()
        initObservers()
    }

    private fun init() {
        weatherHistoryViewModel.getWeatherDetails(args.cityName)
    }

    private fun initView() {
        historyAdapter = HistoryAdapter()

        binding.historyRecyclerView.adapter = historyAdapter

        binding.cityName.text = "${args.cityName} historical"


    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    weatherHistoryViewModel.weather.collect {
                        historyAdapter.submitList(it)
                    }
                }
            }
        }
    }

}