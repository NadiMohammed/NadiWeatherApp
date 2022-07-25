package com.nadi.nadiweatherapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadi.domain.weather.entity.City
import com.nadi.nadiweatherapp.databinding.ItemCitiesSelectorBinding

class CitiesAdapter(private val onClickListener: OnCityClickListener) :
    ListAdapter<City, CitiesAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCitiesSelectorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class ViewHolder(private val binding: ItemCitiesSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: City, clickListener: OnCityClickListener) {
            binding.apply {
                cityNameTxt.append(item.name)

                cityNameTxt.setOnClickListener {
                    clickListener.onCityItemClick(item.name)
                }

                infoImg.setOnClickListener {
                    clickListener.onInfoItemClick(item.name)
                }
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(
        oldSearch: City,
        newSearch: City,
    ): Boolean {
        return oldSearch == newSearch
    }

    override fun areContentsTheSame(
        oldSearch: City,
        newSearch: City,
    ): Boolean {
        return oldSearch == newSearch
    }
}

interface OnCityClickListener {
    fun onCityItemClick(cityName: String)
    fun onInfoItemClick(cityName: String)
}