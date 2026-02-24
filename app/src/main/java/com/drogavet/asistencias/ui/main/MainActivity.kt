package com.drogavet.asistencias.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.drogavet.asistencias.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setupWithNavController(navController)

        // 🔥 Control global del BottomNavigation
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {

                R.id.registrarEmpleadoFragment,
                R.id.verEmpleadosFragment -> {
                    bottomNav.visibility = View.GONE
                }

                else -> {
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }
}