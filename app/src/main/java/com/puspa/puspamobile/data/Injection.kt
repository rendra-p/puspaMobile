package com.puspa.puspamobile.data

import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.ViewModelFactory
import com.puspa.puspamobile.data.remote.retrofit.ApiConfig

object Injection {
    private fun provideUserRepository(): DataRepository {
        val apiService= ApiConfig.getApiService()
        return DataRepository(apiService)
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        val repository =provideUserRepository()
        return ViewModelFactory(repository)
    }
}