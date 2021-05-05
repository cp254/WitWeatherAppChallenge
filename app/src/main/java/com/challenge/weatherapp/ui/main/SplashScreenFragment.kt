package com.challenge.weatherapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.challenge.weatherapp.R
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import org.jetbrains.anko.support.v4.toast


class SplashScreenFragment : Fragment() {
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader()

    }

    fun loader(){
        Handler().postDelayed(Runnable{
            findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
        }, 2 * 1000
        )
    }

    override fun onResume() {
        super.onResume()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SplashScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashScreenFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}