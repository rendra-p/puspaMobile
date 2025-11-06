package com.puspa.puspamobile.data

import com.puspa.puspamobile.data.remote.ApiErrorHandler
import com.puspa.puspamobile.data.remote.response.AddChildRequest
import com.puspa.puspamobile.data.remote.response.ChangePasswordRequest
import com.puspa.puspamobile.data.remote.response.ChildResponse
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import com.puspa.puspamobile.data.remote.response.UpdateProfileRequest
import com.puspa.puspamobile.data.remote.retrofit.ApiService

class DataRepository(private val apiService: ApiService) {
    suspend fun validateToken (): Result<Void?> {
        return try {
            val response = apiService.validateToken()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorMessage = ApiErrorHandler.getErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            when (e) {
                is java.net.UnknownHostException -> { Result.failure(Exception("Server tidak dapat dijangkau.")) }
                is java.net.SocketTimeoutException -> { Result.failure(Exception("Koneksi timeout.")) }
                is retrofit2.HttpException -> { Result.failure(Exception("Kesalahan server (${e.code()})")) }
                else -> { Result.failure(Exception("Terjadi kesalahan: ${e.localizedMessage}")) }
            }
        }
    }
    suspend fun loginUser (loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            val response = apiService.login(loginRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorMessage = ApiErrorHandler.getErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
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
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorMessage = ApiErrorHandler.getErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getProfile (): Result<ProfileResponse> {
        return try {
            val response = apiService.getProfile()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorMessage = ApiErrorHandler.getErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getChild (): Result<ChildResponse> {
        return try {
            val response = apiService.getChildren()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorMessage = ApiErrorHandler.getErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun addChild (addChildRequest: AddChildRequest): Result<Void?> {
        return try {
            val response = apiService.addChild(addChildRequest)
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
    suspend fun changePassword (changePasswordRequest: ChangePasswordRequest): Result<Void?> {
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
    suspend fun updateProfile (guardianId: String, updateProfileRequest: UpdateProfileRequest): Result<Void?> {
        return try {
            val response = apiService.updateProfile(guardianId, updateProfileRequest)
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