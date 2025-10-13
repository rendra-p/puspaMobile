package com.puspa.puspamobile.data

import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import com.puspa.puspamobile.data.remote.retrofit.ApiService

class DataRepository(private val apiService: ApiService) {
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
}