package com.puspa.puspamobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.puspa.puspamobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val chipNavigationBar = binding.bottomMenu

        if (savedInstanceState == null) {
            val navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .setPopUpTo(navController.graph.startDestinationId, false)
                .build()
            navController.navigate(R.id.navigation_home, null, navOptions)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> chipNavigationBar.setItemSelected(R.id.menu_home, true)
                R.id.navigation_jadwal -> chipNavigationBar.setItemSelected(R.id.menu_jadwal, true)
                R.id.navigation_akun -> chipNavigationBar.setItemSelected(R.id.menu_akun, true)
            }
        }

        val navOptionsForTap = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .setPopUpTo(navController.graph.startDestinationId, false)
            .build()

        chipNavigationBar.setOnItemSelectedListener { id ->
            val currentDestination = navController.currentDestination?.id
            when (id) {
                R.id.menu_home ->
                    if (currentDestination != R.id.navigation_home)
                        navController.navigate(R.id.navigation_home, null, navOptionsForTap)

                R.id.menu_jadwal ->
                    if (currentDestination != R.id.navigation_jadwal)
                        navController.navigate(R.id.navigation_jadwal, null, navOptionsForTap)

                R.id.menu_akun ->
                    if (currentDestination != R.id.navigation_akun)
                        navController.navigate(R.id.navigation_akun, null, navOptionsForTap)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}