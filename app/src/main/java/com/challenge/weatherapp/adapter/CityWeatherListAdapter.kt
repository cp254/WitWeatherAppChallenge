package com.challenge.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.weatherapp.R
import com.challenge.weatherapp.api.datamodels.WeatherByCityNameResponse
import com.challenge.weatherapp.api.datamodels.WeatherResponse
import com.challenge.weatherapp.utility.formatToDegrees
import kotlinx.android.synthetic.main.fragment_city_weather_break_down_dialog.view.*
import kotlinx.android.synthetic.main.list_item_city_weather.view.*
import java.text.SimpleDateFormat


class CityWeatherListAdapter(
    private val cityList: List<WeatherByCityNameResponse>,
    val selectedCity : (WeatherByCityNameResponse) -> Unit
) : RecyclerView.Adapter<CityWeatherListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(viewGroup.context)
                        .inflate(R.layout.list_item_city_weather, viewGroup, false),
            selectedCity
        )
    }

    override fun getItemCount(): Int {
        return cityList.count()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindItems(cityList[position])
    }

    class ViewHolder(
            itemView: View,
            val selectedCity: (WeatherByCityNameResponse) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: WeatherByCityNameResponse) {
            itemView.apply {
                textViewCityName.text = item.name
                val weather = item.weather?: emptyList()
                textViewCondition.text = if(weather.isNotEmpty())weather[0].description else "No data"
                textViewDegress.text = item.main?.temp?.formatToDegrees()
                val simpleDateFormat = SimpleDateFormat("dd\nMMM")
                val dateString = simpleDateFormat.format(item.dt)
                textViewDate.text = "${dateString}"
                textViewHumidity.text = "${item.main?.humidity}%"
                setOnClickListener {
                    selectedCity(item)
                }
            }
        }
    }
}