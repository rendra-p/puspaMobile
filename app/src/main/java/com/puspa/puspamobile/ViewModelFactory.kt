package com.puspa.puspamobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.ui.auth.LoginViewModel
import com.puspa.puspamobile.ui.auth.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: DataRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}