package com.puspa.puspamobile.ui.error

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.puspa.puspamobile.R
import com.puspa.puspamobile.databinding.ActivityNoInternetBinding
import com.puspa.puspamobile.ui.Splash

class NoInternet : AppCompatActivity() {
    private lateinit var binding: ActivityNoInternetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNoInternet.setOnClickListener {
            val intent = Intent(this, Splash::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}