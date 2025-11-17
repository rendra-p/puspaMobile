package com.puspa.puspamobile.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.ForgotPasswordRequest
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _forgotPasswordResult = MutableLiveData<Result<Void?>>()
    val forgotPasswordResult: LiveData<Result<Void?>> = _forgotPasswordResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.forgotPassword(forgotPasswordRequest)
                _forgotPasswordResult.value = result
            } catch (e: Exception) {
                _forgotPasswordResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}