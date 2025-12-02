package com.puspa.puspamobile.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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