package com.challenge.weatherapp.api.datamodels


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherByCityNameResponse(
    @SerializedName("base")
    val base: String? = null, // stations
    @SerializedName("clouds")
    val clouds: Clouds? = null,
    @SerializedName("cod")
    val cod: Int? = null, // 200
    @SerializedName("coord")
    val coord: Coord? = null,
    @SerializedName("dt")
    val dt: Int? = null, // 1620186678
    @SerializedName("id")
    val id: Int? = null, // 2643743
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("name")
    val name: String? = null, // London
    @SerializedName("sys")
    val sys: Sys? = null,
    @SerializedName("timezone")
    val timezone: Int? = null, // 3600
    @SerializedName("visibility")
    val visibility: Int? = null, // 10000
    @SerializedName("weather")
    val weather: List<Weather>? = null,
    @SerializedName("wind")
    val wind: Wind? = null
) : Parcelable {
    @Parcelize
    data class Clouds(
        @SerializedName("all")
        val all: Int? = null // 90
    ): Parcelable
    @Parcelize
    data class Coord(
        @SerializedName("lat")
        val lat: Double? = null, // 51.5085
        @SerializedName("lon")
        val lon: Double? = null // -0.1257
    ): Parcelable
    @Parcelize
    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double? = null, // 275.58
        @SerializedName("humidity")
        val humidity: Int? = null, // 81
        @SerializedName("pressure")
        val pressure: Int? = null, // 1006
        @SerializedName("temp")
        val temp: Double? = null, // 277.8
        @SerializedName("temp_max")
        val tempMax: Double? = null, // 278.15
        @SerializedName("temp_min")
        val tempMin: Double? = null // 277.04
    ): Parcelable
    @Parcelize
    data class Sys(
        @SerializedName("country")
        val country: String? = null, // GB
        @SerializedName("id")
        val id: Int? = null, // 1414
        @SerializedName("sunrise")
        val sunrise: Int? = null, // 1620188686
        @SerializedName("sunset")
        val sunset: Int? = null, // 1620242972
        @SerializedName("type")
        val type: Int? = null // 1
    ): Parcelable
    @Parcelize
    data class Weather(
        @SerializedName("description")
        val description: String? = null, // overcast clouds
        @SerializedName("icon")
        val icon: String? = null, // 04n
        @SerializedName("id")
        val id: Int? = null, // 804
        @SerializedName("main")
        val main: String? = null // Clouds
    ): Parcelable
    @Parcelize
    data class Wind(
        @SerializedName("deg")
        val deg: Int? = null, // 340
        @SerializedName("speed")
        val speed: Double? = null // 2.57
    ) : Parcelable
}