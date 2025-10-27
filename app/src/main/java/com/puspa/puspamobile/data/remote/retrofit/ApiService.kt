package com.puspa.puspamobile.data.remote.retrofit

import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.LogoutResponse
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import com.puspa.puspamobile.data.remote.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("auth/protected")
    suspend fun validateToken(): TokenResponse
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
    @POST("auth/logout")
    suspend fun logout(): LogoutResponse
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse
    @GET("my/profile")
    suspend fun getProfile(): ProfileResponse
}