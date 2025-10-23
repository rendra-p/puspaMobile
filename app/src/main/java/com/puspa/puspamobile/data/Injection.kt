package com.puspa.puspamobile.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.ViewModelFactory
import com.puspa.puspamobile.data.local.TokenDataStore
import com.puspa.puspamobile.data.remote.retrofit.ApiConfig

object Injection {
    private fun provideTokenDataStore(context: Context): TokenDataStore {
        return TokenDataStore.getInstance(context)
    }

    private fun provideUserRepository(tokenDataStore: TokenDataStore): DataRepository {
        val apiService= ApiConfig.getApiService(tokenDataStore)
        return DataRepository(apiService)
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        val tokenDataStore = provideTokenDataStore(context)
        val repository =provideUserRepository(tokenDataStore)
        return ViewModelFactory(repository, tokenDataStore)
    }
}