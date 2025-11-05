package com.puspa.puspamobile.data.remote.retrofit

import com.puspa.puspamobile.data.remote.response.ChangePasswordRequest
import com.puspa.puspamobile.data.remote.response.ChildResponse
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import com.puspa.puspamobile.data.remote.response.UpdateProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("auth/protected")
    suspend fun validateToken(): Response<Void>
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
    @POST("auth/logout")
    suspend fun logout(): Response<Void>
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>
    @GET("my/profile")
    suspend fun getProfile(): Response<ProfileResponse>
    @GET("my/children")
    suspend fun getChildren(): Response<ChildResponse>
    @POST("my/update-password")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<Void>
    @POST("my/profile/{guardian_id}")
    suspend fun updateProfile(
        @Path("guardian_id") guardianId: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<Void>
}