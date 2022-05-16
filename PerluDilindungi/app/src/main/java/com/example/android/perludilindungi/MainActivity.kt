package com.example.android.perludilindungi

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.android.perludilindungi.checkin.CheckInActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    lateinit var checkInBtn : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PerluDilindungi)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        locationPermission()
        // Bottom Navigation Configuration
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navController = findNavController(R.id.fragmentContainerView)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.newsFragment, R.id.lokasiVaksinFragment, R.id.bookmarkFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Setup checkin fab on click
        checkInBtn = findViewById(R.id.checkInButton)
        checkInBtn.setOnClickListener {
            val i = Intent(this, CheckInActivity::class.java)
            startActivity(i)
        }

        // bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.newsFragment -> {
                    navController.navigate(R.id.newsFragment)
                }
                R.id.lokasiVaksinFragment -> {
                    navController.navigate(R.id.lokasiVaksinFragment)
                }
                R.id.bookmarkFragment -> {
                    navController.navigate(R.id.bookmarkFragment)
                }
            }
            true
        }
    }

    fun locationPermission(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (this as Activity?)!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                101
            )
        }

        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                (this as Activity?)!!,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
        }
    }

}