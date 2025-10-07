package com.puspa.puspamobile.ui.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.puspa.puspamobile.R
import com.puspa.puspamobile.databinding.ActivityGmailBinding

class GmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}