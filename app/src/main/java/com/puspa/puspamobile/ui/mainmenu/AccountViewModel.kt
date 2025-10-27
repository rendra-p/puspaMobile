package com.puspa.puspamobile.ui.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.LogoutResponse
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> = _profileResult

    private val _logoutResult = MutableLiveData<Result<LogoutResponse>>()
    val logoutResult: LiveData<Result<LogoutResponse>> = _logoutResult

    fun getProfile() {
        viewModelScope.launch {
            try {
                val result = repository.getProfile()
                _profileResult.value = result
            } catch (e: Exception) {
                _profileResult.value = Result.failure(e)
            }
        }
    }

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