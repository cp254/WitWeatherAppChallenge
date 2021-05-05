package com.challenge.weatherapp.utility


fun Double?.formatToDegrees(): String {
    val formattedDegree = if (this != null)
        String.format("%.1f", this)
    else
        "0.0"
    return formattedDegree + "\u00B0"
}
