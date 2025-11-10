package com.puspa.puspamobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.ismaeldivita.chipnavigation.ChipNavigationBar
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

        val chipNavigationBar = findViewById<ChipNavigationBar>(R.id.bottom_menu)
        chipNavigationBar.setItemSelected(R.id.menu_home, true)

        chipNavigationBar.setOnItemSelectedListener { id ->
            val currentDestination = navController.currentDestination?.id
            when (id) {
                R.id.menu_home -> if (currentDestination != R.id.navigation_home)
                    navController.navigate(R.id.navigation_home)

                R.id.menu_jadwal -> if (currentDestination != R.id.navigation_jadwal)
                    navController.navigate(R.id.navigation_jadwal)

                R.id.menu_artikel -> if (currentDestination != R.id.navigation_artikel)
                    navController.navigate(R.id.navigation_artikel)

                R.id.menu_akun -> if (currentDestination != R.id.navigation_akun)
                    navController.navigate(R.id.navigation_akun)
            }
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_jadwal,
                R.id.navigation_artikel,
                R.id.navigation_akun
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}