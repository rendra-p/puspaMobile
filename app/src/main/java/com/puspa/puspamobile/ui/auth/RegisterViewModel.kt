package com.puspa.puspamobile.ui.auth

import com.puspa.puspamobile.data.DataRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: DataRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(registerRequest: RegisterRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.registerUser(registerRequest)
            _registerResult.value = result
            _isLoading.value = false
        }
    }
}
