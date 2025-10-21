package com.puspa.puspamobile.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.puspa.puspamobile.databinding.ActivitySplashBinding
import com.puspa.puspamobile.ui.auth.BoardingActivity
import com.puspa.puspamobile.ui.error.NetworkUtils
import com.puspa.puspamobile.ui.error.NoInternet

class Splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            navigationBarColor = Color.TRANSPARENT
            statusBarColor = Color.TRANSPARENT
            WindowCompat.setDecorFitsSystemWindows(this, false)
            WindowInsetsControllerCompat(this, decorView).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
                hide(WindowInsetsCompat.Type.navigationBars() or WindowInsetsCompat.Type.statusBars())
            }
        }

        val progressAnimation = ObjectAnimator.ofInt(binding.progressBar, "progress", 0, 100)
        progressAnimation.duration = SPLASH_TIME_OUT
        progressAnimation.interpolator = LinearInterpolator()
        progressAnimation.start()

        Handler(Looper.getMainLooper()).postDelayed({
            if (NetworkUtils.isInternetAvailable(this)) {
                startActivity(Intent(this, BoardingActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, NoInternet::class.java))
                finish()
            }
        }, SPLASH_TIME_OUT)
    }
}