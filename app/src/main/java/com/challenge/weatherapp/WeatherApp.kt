package com.challenge.weatherapp

import android.app.Application
import timber.log.Timber

class WeatherApp : Application() {

    companion object {
        lateinit var instance: WeatherApp private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}