package com.puspa.puspamobile.data.local

import android.content.Context
import androidx.core.content.edit

class UserPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveUserId(userId: String) {
        sharedPreferences.edit { putString("user_id", userId) }
    }

    fun removeUserId() {
        sharedPreferences.edit { remove("user_id") }
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("user_id", null)
    }
}