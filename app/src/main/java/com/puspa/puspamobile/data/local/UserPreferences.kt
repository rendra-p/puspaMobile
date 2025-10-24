package com.puspa.puspamobile.data.local

import android.content.Context
import androidx.core.content.edit

class UserPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

}