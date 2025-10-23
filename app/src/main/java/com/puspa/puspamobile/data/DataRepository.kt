package com.puspa.puspamobile.data

import android.content.Context
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import com.puspa.puspamobile.data.remote.response.TokenResponse
import com.puspa.puspamobile.data.remote.retrofit.ApiConfig
import com.puspa.puspamobile.data.remote.retrofit.ApiService

class DataRepository(private val apiService: ApiService) {
    suspend fun validateToken (): Result<TokenResponse> {
        return try {
            val response = apiService.validateToken()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun loginUser (loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            val response = apiService.login(loginRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun registerUser (registerRequest: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = apiService.register(registerRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(apiService: ApiService): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(apiService).also { instance = it }
            }
    }
}