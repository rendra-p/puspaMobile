package com.puspa.puspamobile.data.remote.retrofit

import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse
}