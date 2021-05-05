package com.challenge.weatherapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.weatherapp.api.datamodels.WeatherByCityNameResponse
import com.challenge.weatherapp.api.datamodels.WeatherResponse
import com.challenge.weatherapp.repository.MainRepository

class MainViewModel : ViewModel() {

    var weatherByCityNameLiveData: LiveData<WeatherByCityNameResponse>? = null
    var weatherByLocationLiveData: LiveData<WeatherByCityNameResponse>? = null

    fun getLocationWeatherData(latitude : String, longitude : String) : LiveData<WeatherByCityNameResponse>? {
        weatherByLocationLiveData = MainRepository.getWeatherByCoordinates(latitude,longitude)
        return weatherByLocationLiveData
    }

    fun getEuropeCitiesLocationData(cityName : String) : LiveData<WeatherByCityNameResponse>? {
        weatherByCityNameLiveData = MainRepository.getWeatherByCityName(cityName)
        return weatherByCityNameLiveData
    }
}