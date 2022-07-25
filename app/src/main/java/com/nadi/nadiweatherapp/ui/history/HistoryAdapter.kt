package com.nadi.nadiweatherapp.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadi.domain.weather.entity.Weather
import com.nadi.nadiweatherapp.databinding.ItemHistoryBinding
import com.squareup.picasso.Picasso

class HistoryAdapter :
    ListAdapter<Weather, HistoryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Weather) {
            binding.apply {
                dateAndTimeTxt.append(item.dateTime)
                descriptionTxt.append(item.description)
                tempTxt.append(item.temperature.toString())
                Picasso.get().load(item.icon).into(cityImg)
            }
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(
        oldSearch: Weather,
        newSearch: Weather,
    ): Boolean {
        return oldSearch == newSearch
    }

    override fun areContentsTheSame(
        oldSearch: Weather,
        newSearch: Weather,
    ): Boolean {
        return oldSearch == newSearch
    }
}