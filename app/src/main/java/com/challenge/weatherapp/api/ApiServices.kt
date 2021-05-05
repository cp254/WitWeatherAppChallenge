package com.challenge.weatherapp.api

import androidx.lifecycle.LiveData
import com.challenge.weatherapp.BuildConfig
import com.challenge.weatherapp.api.datamodels.WeatherByCityNameResponse
import com.challenge.weatherapp.api.datamodels.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    companion object {
        const val end_point: String = "/data/2.5/"
    }


    @GET(end_point+"weather?")
    fun getWeatherByCityName(
        @Query(value = "q", encoded = true) city: String,
        @Query("appid") appId: String = BuildConfig.API_KEY
    ): Call<WeatherByCityNameResponse>

    @GET(end_point+"weather?")
    fun getWeatherByCoordinates(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") appId: String = BuildConfig.API_KEY
    ): Call<WeatherByCityNameResponse>
}