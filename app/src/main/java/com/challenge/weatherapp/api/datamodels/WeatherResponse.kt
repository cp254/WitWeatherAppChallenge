package com.challenge.weatherapp.api.datamodels


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WeatherResponse(
    @SerializedName("base")
    val base: String? = null, // stations
    @SerializedName("clouds")
    val clouds: Clouds? = null,
    @SerializedName("cod")
    val cod: Int? = null, // 200
    @SerializedName("coord")
    val coord: Coord? = null,
    @SerializedName("dt")
    val dt: Int? = null, // 1560350645
    @SerializedName("id")
    val id: Int? = null, // 420006353
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("name")
    val name: String? = null, // Mountain View
    @SerializedName("sys")
    val sys: Sys? = null,
    @SerializedName("timezone")
    val timezone: Int? = null, // -25200
    @SerializedName("visibility")
    val visibility: Int? = null, // 16093
    @SerializedName("weather")
    val weather: List<Weather>? = null,
    @SerializedName("wind")
    val wind: Wind? = null
): Parcelable {
    @Parcelize
    data class Clouds(
        @SerializedName("all")
        val all: Int? = null // 1
    ) : Parcelable
    @Parcelize
    data class Coord(
        @SerializedName("lat")
        val lat: Double? = null, // 37.39
        @SerializedName("lon")
        val lon: Double? = null // -122.08
    ): Parcelable
    @Parcelize
    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double? = null, // 281.86
        @SerializedName("humidity")
        val humidity: Int? = null, // 100
        @SerializedName("pressure")
        val pressure: Int? = null, // 1023
        @SerializedName("temp")
        val temp: Double? = null, // 282.55
        @SerializedName("temp_max")
        val tempMax: Double? = null, // 284.26
        @SerializedName("temp_min")
        val tempMin: Double? = null // 280.37
    ): Parcelable
    @Parcelize
    data class Sys(
        @SerializedName("country")
        val country: String? = null, // US
        @SerializedName("id")
        val id: Int? = null, // 5122
        @SerializedName("message")
        val message: Double? = null, // 0.0139
        @SerializedName("sunrise")
        val sunrise: Int? = null, // 1560343627
        @SerializedName("sunset")
        val sunset: Int? = null, // 1560396563
        @SerializedName("type")
        val type: Int? = null // 1
    ): Parcelable
    @Parcelize
    data class Weather(
        @SerializedName("description")
        val description: String? = null, // clear sky
        @SerializedName("icon")
        val icon: String? = null, // 01d
        @SerializedName("id")
        val id: Int? = null, // 800
        @SerializedName("main")
        val main: String? = null // Clear
    ): Parcelable
    @Parcelize
    data class Wind(
        @SerializedName("deg")
        val deg: Int? = null, // 350
        @SerializedName("speed")
        val speed: Double? = null // 1.5
    ): Parcelable
}