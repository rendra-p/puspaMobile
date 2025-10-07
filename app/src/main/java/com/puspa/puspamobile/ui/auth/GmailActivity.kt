package com.puspa.puspamobile.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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

        binding.imgbtnBack.setOnClickListener {
            finish()
        }
        binding.btnOpenEmail.setOnClickListener {
            openEmailApp()
        }
    }

    private fun openEmailApp() {
        val intent = Intent(Intent.CATEGORY_APP_EMAIL).apply {
            addCategory(Intent.CATEGORY_APP_EMAIL)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            tryOpenAlternative()
        }
    }

    private fun tryOpenAlternative() {
        val alternativeIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
        }

        if (alternativeIntent.resolveActivity(packageManager) != null) {
            startActivity(alternativeIntent)
        } else {
            Toast.makeText(this, "Tidak ada aplikasi email yang terinstal.", Toast.LENGTH_LONG).show()
        }
    }
}