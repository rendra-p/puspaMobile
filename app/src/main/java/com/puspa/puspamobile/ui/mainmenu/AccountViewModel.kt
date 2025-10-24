package com.puspa.puspamobile.ui.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.LogoutResponse
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _logoutResult = MutableLiveData<Result<LogoutResponse>>()
    val logoutResult: LiveData<Result<LogoutResponse>> = _logoutResult

    fun logoutUser() {
        viewModelScope.launch {
            try {
                val result = repository.logoutUser()
                _logoutResult.value = result
            } catch (e: Exception) {
                _logoutResult.value = Result.failure(e)
            }
        }
    }
}