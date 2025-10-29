package com.puspa.puspamobile.ui.error

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.puspa.puspamobile.R
import com.puspa.puspamobile.databinding.ActivityGeneralErrorBinding
import com.puspa.puspamobile.ui.Splash

class GeneralError : AppCompatActivity() {
    private lateinit var binding: ActivityGeneralErrorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
                isAppearanceLightNavigationBars = true
            }
        }

        binding.btnGeneralError.setOnClickListener {
            startActivity(Intent(this, Splash::class.java))
            finish()
        }
    }
}