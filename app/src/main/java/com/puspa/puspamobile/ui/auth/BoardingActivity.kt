package com.puspa.puspamobile.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.puspa.puspamobile.R
import com.puspa.puspamobile.databinding.ActivityBoardingBinding

class BoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasukBoarding.setOnClickListener {
            startActivity(Intent(this@BoardingActivity, LoginActivity::class.java))
        }
        binding.btnDaftarBoarding.setOnClickListener {
            startActivity(Intent(this@BoardingActivity, RegisterActivity::class.java))
        }
    }
}