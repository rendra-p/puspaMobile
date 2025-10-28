package com.puspa.puspamobile.data

import android.content.Context
import com.puspa.puspamobile.data.remote.ApiErrorHandler
import com.puspa.puspamobile.data.remote.response.ChangePasswordRequest
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import com.puspa.puspamobile.data.remote.retrofit.ApiConfig
import com.puspa.puspamobile.data.remote.retrofit.ApiService

class DataRepository(private val apiService: ApiService) {
    suspend fun validateToken (): Result<Void?> {
        return try {
            val response = apiService.validateToken()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(Exception(ApiErrorHandler.getErrorMessage(response)))
            }
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
    suspend fun logoutUser (): Result<Void?> {
        return try {
            val response = apiService.logout()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorMessage = ApiErrorHandler.getErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
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
    suspend fun getProfile (): Result<ProfileResponse> {
        return try {
            val response = apiService.getProfile()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Result<Void?> {
        return try {
            val response = apiService.changePassword(changePasswordRequest)
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorMessage = ApiErrorHandler.getErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
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