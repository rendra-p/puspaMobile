package com.puspa.puspamobile.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.animation.LinearInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.puspa.puspamobile.MainActivity
import com.puspa.puspamobile.data.local.TokenDataStore
import com.puspa.puspamobile.databinding.ActivitySplashBinding
import com.puspa.puspamobile.ui.auth.BoardingActivity
import com.puspa.puspamobile.ui.error.NetworkUtils
import com.puspa.puspamobile.ui.error.NoInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.retrofit.ApiConfig
import com.puspa.puspamobile.ui.error.GeneralError
import kotlinx.coroutines.delay

class Splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var repository: DataRepository
    private lateinit var tokenDataStore: TokenDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenDataStore = TokenDataStore.getInstance(this)
        val apiService = ApiConfig.getApiService(tokenDataStore)
        repository = DataRepository.getInstance(apiService)

        setupTransparentSystemUI(window)
        startProgressAnimation()

        lifecycleScope.launch {
            val splashStartTime = System.currentTimeMillis()
            val token = getTokenFromDataStore()

            if (!NetworkUtils.isInternetAvailable(this@Splash)) {
                goTo(NoInternet::class.java)
                return@launch
            }

            val nextActivity = if (token.isNullOrEmpty()) {
                BoardingActivity::class.java
            } else {
                val result = withContext(Dispatchers.IO) {
                    repository.validateToken()
                }
                if (result.isSuccess) {
                    MainActivity::class.java
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "Terjadi kesalahan"
                    if (errorMsg.contains("Server tidak dapat dijangkau", ignoreCase = true)) {
                        goTo(GeneralError::class.java)
                        return@launch
                    } else {
                        BoardingActivity::class.java
                    }
                }
            }

            val elapsed = System.currentTimeMillis() - splashStartTime
            val remaining = 2000L - elapsed
            if (remaining > 0) delay(remaining)

            goTo(nextActivity)
        }
    }

    private fun goTo(activity: Class<*>) {
        startActivity(Intent(this, activity))
        finish()
    }

    private suspend fun getTokenFromDataStore(): String? {
        val dataStore = TokenDataStore.getInstance(this)
        return dataStore.token.firstOrNull()
    }

    private fun setupTransparentSystemUI(window: Window) {
        window.apply {
            navigationBarColor = Color.TRANSPARENT
            statusBarColor = Color.TRANSPARENT
            WindowCompat.setDecorFitsSystemWindows(this, false)
            WindowInsetsControllerCompat(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
                isAppearanceLightNavigationBars = true
            }
        }
    }

    private fun startProgressAnimation() {
        ObjectAnimator.ofInt(binding.progressBar, "progress", 0, 100).apply {
            this.duration = 2000L
            interpolator = android.view.animation.LinearInterpolator()
            start()
        }
    }
}