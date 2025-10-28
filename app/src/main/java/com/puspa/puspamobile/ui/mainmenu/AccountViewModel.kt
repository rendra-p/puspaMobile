package com.puspa.puspamobile.ui.mainmenu

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.ChangePasswordRequest
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import kotlinx.coroutines.launch
import org.json.JSONObject

class AccountViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> = _profileResult

    private val _changePasswordResult = MutableLiveData<Result<Void?>>()
    val changePasswordResult: LiveData<Result<Void?>> = _changePasswordResult

    private val _logoutResult = MutableLiveData<Result<Void?>>()
    val logoutResult: LiveData<Result<Void?>> = _logoutResult

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

    fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        viewModelScope.launch {
            try {
                val result = repository.changePassword(changePasswordRequest)
                _changePasswordResult.value = result
            } catch (e: Exception) {
                _changePasswordResult.value = Result.failure(e)
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