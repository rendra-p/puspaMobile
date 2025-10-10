package com.puspa.puspamobile.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.puspa.puspamobile.databinding.ActivitySplashBinding
import com.puspa.puspamobile.ui.auth.BoardingActivity
import com.puspa.puspamobile.ui.error.NetworkUtils
import com.puspa.puspamobile.ui.error.NoInternet

class Splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            if (NetworkUtils.isInternetAvailable(this)) {
                startActivity(Intent(this, BoardingActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, NoInternet::class.java))
                finish()
            }
        }, 3000)
    }
}