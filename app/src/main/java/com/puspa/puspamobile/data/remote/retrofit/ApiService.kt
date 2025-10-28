package com.puspa.puspamobile.data.remote.retrofit

import com.puspa.puspamobile.data.remote.response.ChangePasswordRequest
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("auth/protected")
    suspend fun validateToken(): Response<Void>
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
    @POST("auth/logout")
    suspend fun logout(): Response<Void>
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse
    @GET("my/profile")
    suspend fun getProfile(): ProfileResponse
    @POST("my/update-password")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<Void>
    @POST("my/profile/{guardian_id}")
    suspend fun updateProfile(): Response<Void>
}