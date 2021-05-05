package com.challenge.weatherapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.weatherapp.R
import com.challenge.weatherapp.adapter.CityWeatherListAdapter
import com.challenge.weatherapp.api.datamodels.WeatherByCityNameResponse
import com.challenge.weatherapp.api.datamodels.WeatherResponse
import com.challenge.weatherapp.utility.formatToDegrees
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import kotlinx.android.synthetic.main.list_item_city_weather.*
import kotlinx.android.synthetic.main.list_item_city_weather.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import java.text.SimpleDateFormat

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val europeWeatherData = arrayListOf<WeatherByCityNameResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        getEuropesLocationWeatherData()
        getLastLocation()
    }

    private fun getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {
                // get last location
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
                if (context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED && context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions()
                    return
                } else fusedLocationClient.lastLocation
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val mLastLocation: Location = task.result
                            getCurrentLocationWeatherData(
                                mLastLocation.latitude.toString(),
                                mLastLocation.longitude.toString()
                            )

                        } else {
                            requestNewLocationData()
                        }

                    }

            } else {
                val snack = Snackbar.make(
                    rootView,
                    "Enable your location and try again",
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setAction("Click Me") {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                snack.show()

            }
        } else {
            // request for permissions
            requestPermissions()
        }
    }

    fun getCurrentLocationWeatherData(lat: String, lon: String) {
        viewModel.getLocationWeatherData(lat,lon)
            ?.observe(viewLifecycleOwner, Observer { cityWeather ->
                setupViews(cityWeather)
            })
    }

    fun setupViews(currentLocationData: WeatherByCityNameResponse){
        currentLocationLoadingView.visibility = View.GONE
        currentLocationWeatherView.visibility = View.VISIBLE
        currentCityWeather.text = "The weather in ${currentLocationData.name} is"
        textViewCityName.text = currentLocationData.name
        val weather = currentLocationData.weather?: emptyList()
        textViewCondition.text = if(weather.isNotEmpty())weather[0].description else "No data"
        textViewDegress.text = currentLocationData.main?.temp?.formatToDegrees()
        val simpleDateFormat = SimpleDateFormat("dd\nMMM")
        val dateString = simpleDateFormat.format(currentLocationData.dt)
        textViewDate.text = "$dateString"
        textViewHumidity.text = "${currentLocationData.main?.humidity}%"
    }

    fun setupRecyclerView(europeCities: List<WeatherByCityNameResponse>) {
        cityListRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        cityListRecyclerView.adapter = CityWeatherListAdapter(
            cityList = europeCities,
            selectedCity = ::navigateToSelectedCity
        )
        europeWeatherLoadingView.visibility = View.GONE
        cityListRecyclerView.visibility = View.VISIBLE
    }

    fun navigateToSelectedCity(cityWeatherData: WeatherByCityNameResponse) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToCityWeatherBreakDownDialogFragment(weatherDetails = cityWeatherData))
    }

    fun getEuropesLocationWeatherData() {
        val citiesOfEurope = listOf<String>(
            "Lisbon",
            "Paris",
            "Madrid",
            "Berlin",
            "Rome",
            "London",
            "Prague",
            "Dublin",
            "Vienna",
            "Copenhagen"
        )
        europeWeatherData.clear()
        for (c in 0..citiesOfEurope.lastIndex) {
            viewModel.getEuropeCitiesLocationData(citiesOfEurope[c])
                ?.observe(viewLifecycleOwner, Observer { cityWeather ->
                    if (europeWeatherData.contains(cityWeather).not())
                        europeWeatherData.add(cityWeather)
                    setupRecyclerView(europeWeatherData)
                })

        }
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        // setting LocationRequest on FusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            getCurrentLocationWeatherData(
                mLastLocation.latitude.toString(),
                mLastLocation.longitude.toString()
            )
        }
    }

    // method to check for permissions
    private fun checkPermissions(): Boolean {
        return context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } == PackageManager.PERMISSION_GRANTED && context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } == PackageManager.PERMISSION_GRANTED

    }

    // method to request for permissions
    private fun requestPermissions() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 184
            )
        }
    }

    // check if location is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager =
            context?.let { it.getSystemService(Context.LOCATION_SERVICE) } as LocationManager?
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 184) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                toast(getString(R.string.error_unable_to_get_coordinated))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }


}