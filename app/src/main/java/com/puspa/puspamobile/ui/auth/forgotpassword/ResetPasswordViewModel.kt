package com.puspa.puspamobile.ui.auth.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.ResetPasswordRequest
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val repository: DataRepository) : ViewModel() {
    private val _resetPasswordResult = MutableLiveData<Result<Void?>>()
    val resetPasswordResult: LiveData<Result<Void?>> = _resetPasswordResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun resetPassword(token: String, email: String, resetPasswordRequest: ResetPasswordRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.resetPassword(token, email, resetPasswordRequest)
                _resetPasswordResult.value = result
            } catch (e: Exception) {
                _resetPasswordResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}