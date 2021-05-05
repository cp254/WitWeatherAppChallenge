package com.challenge.weatherapp.repository

import androidx.lifecycle.MutableLiveData
import com.challenge.weatherapp.api.ApiClient

import android.util.Log
import com.challenge.weatherapp.api.datamodels.WeatherByCityNameResponse
import com.challenge.weatherapp.api.datamodels.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object MainRepository {

    val weatherByCoordinates = MutableLiveData<WeatherByCityNameResponse>()
    val weatherByByCityName = MutableLiveData<WeatherByCityNameResponse>()

    fun getWeatherByCoordinates(lat : String, lon : String): MutableLiveData<WeatherByCityNameResponse> {

        val call = ApiClient.apiInterface.getWeatherByCoordinates(lat,lon)

        call.enqueue(object: Callback<WeatherByCityNameResponse> {
            override fun onFailure(call: Call<WeatherByCityNameResponse>, t: Throwable) {
                Timber.e("DEBUG : ${t.printStackTrace()}" )
            }

            override fun onResponse(
                call: Call<WeatherByCityNameResponse>,
                response: Response<WeatherByCityNameResponse>
            ) {
                Timber.e("DEBUG : ${response.body().toString()}")
                weatherByCoordinates.value = response.body()
            }
        })

        return weatherByCoordinates
    }

    fun getWeatherByCityName(city : String): MutableLiveData<WeatherByCityNameResponse> {

        val call = ApiClient.apiInterface.getWeatherByCityName(city)

        call.enqueue(object: Callback<WeatherByCityNameResponse> {
            override fun onFailure(call: Call<WeatherByCityNameResponse>, t: Throwable) {
                Timber.e("DEBUG : ${t.printStackTrace()}" )
            }

            override fun onResponse(
                call: Call<WeatherByCityNameResponse>,
                response: Response<WeatherByCityNameResponse>
            ) {
                Timber.e("DEBUG : ${response.body().toString()}")
                weatherByByCityName.value = response.body()
            }
        })

        return weatherByByCityName
    }
}
