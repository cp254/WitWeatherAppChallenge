package com.challenge.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.challenge.weatherapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }

    override fun onBackPressed() {
        val currentFragment = findNavController(R.id.nav_host_fragment).currentDestination
        when (currentFragment?.id) {
            R.id.mainFragment -> {
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}