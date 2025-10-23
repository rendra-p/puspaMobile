package com.puspa.puspamobile.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.local.TokenDataStore
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val repository: DataRepository,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(loginRequest: LoginRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.loginUser(loginRequest)
                _loginResult.value = result
                result.onSuccess { response ->
                    response.data?.token?.let { token ->
                        tokenDataStore.saveToken(token)
                    }
                }
            } catch (e: Exception) {
                val error = if (e is HttpException && e.code() == 401) {
                    "Invalid email or password"
                } else {
                    "An unknown error occurred"
                }
                _loginResult.value = Result.failure(Exception(error))
            } finally {
                _isLoading.value = false
            }
        }
    }
}
