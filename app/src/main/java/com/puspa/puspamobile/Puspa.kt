package com.puspa.puspamobile

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class Puspa : Application() {
    override fun onCreate() {
        super.onCreate()

        // Force light mode device
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}