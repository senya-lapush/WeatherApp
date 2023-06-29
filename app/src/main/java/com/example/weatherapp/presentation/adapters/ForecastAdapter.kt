package com.example.weatherapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemWeatherInfoBinding
import com.example.weatherapp.domain.WeatherEntity

class ForecastAdapter(private val context: Context) : RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(val binding: ItemWeatherInfoBinding) : RecyclerView.ViewHolder(binding.root)

    var list: List<WeatherEntity> = listOf()
        set(value) {
            field = value
            notifyItemRangeChanged(0, list.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = list[position]

        val tempTemplate = context.resources.getString(R.string.degrees)
        val pressureTemplate = context.resources.getString(R.string.mmHG)
        val humidityTemplate = context.resources.getString(R.string.percent)
        val windTemplate = context.resources.getString(R.string.m_in_sec)

        with(holder.binding) {
            with(weather) {
                tvDate.text = dt
                tvTemp.text = String.format(tempTemplate, temperature)
                tvPressure.text = String.format(pressureTemplate, pressure)
                tvHumidity.text = String.format(humidityTemplate, humidity)
                tvWind.text = String.format(windTemplate, windSpeed)
            }
        }
    }

}