package com.challenge.weatherapp.ui.main

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.challenge.weatherapp.R
import com.challenge.weatherapp.utility.formatToDegrees
import kotlinx.android.synthetic.main.fragment_city_weather_break_down_dialog.*
import kotlinx.android.synthetic.main.fragment_city_weather_break_down_dialog.view.*
import kotlinx.android.synthetic.main.list_item_city_weather.*
import kotlinx.android.synthetic.main.list_item_city_weather.view.*
import java.text.SimpleDateFormat

class CityWeatherBreakDownDialogFragment : BottomSheetDialogFragment() {

    val args : CityWeatherBreakDownDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_weather_break_down_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    fun setupViews(){
        cityName.text = "The weather in ${args.weatherDetails.name} is:"
        moreInfoLayout.visibility = View.VISIBLE
        textViewCityName.text = args.weatherDetails.name
        val weather = args.weatherDetails.weather?: emptyList()
        textViewCondition.text = if(weather.isNotEmpty())weather[0].description else "No data"
        textViewDegress.text = args.weatherDetails.main?.temp?.formatToDegrees()
        val simpleDateFormat = SimpleDateFormat("dd\nMMM")
        val dateString = simpleDateFormat.format(args.weatherDetails.dt)
        textViewDate.text = "${dateString}"
        textViewHumidity.text = "${args.weatherDetails.main?.humidity}%"
        textViewHighTempValue.text = args.weatherDetails.main?.tempMax.formatToDegrees()
        textViewLowTempValue.text = args.weatherDetails.main?.tempMin.formatToDegrees()
        textViewVisibilityValue.text = "${args.weatherDetails.visibility} M"
        textViewWindSpeedValue.text = "${args.weatherDetails.wind?.speed} Km/hr"

    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(itemCount: Int): CityWeatherBreakDownDialogFragment =
            CityWeatherBreakDownDialogFragment()
                .apply {
                arguments = Bundle().apply {
                }
            }

    }
}