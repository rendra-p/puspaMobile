package com.puspa.puspamobile.data.remote.retrofit

import com.puspa.puspamobile.data.remote.response.AddChildRequest
import com.puspa.puspamobile.data.remote.response.AssesmentsResponse
import com.puspa.puspamobile.data.remote.response.ChangePasswordRequest
import com.puspa.puspamobile.data.remote.response.ChildResponse
import com.puspa.puspamobile.data.remote.response.ForgotPasswordRequest
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): Response<Void>
    @GET("my/profile")
    suspend fun getProfile(): Response<ProfileResponse>
    @GET("my/children")
    suspend fun getChildren(): Response<ChildResponse>
    @POST("my/children")
    suspend fun addChild(
        @Body addChildRequest: AddChildRequest
    ): Response<Void>
    @GET("my/assessments")
    suspend fun getAssesments(): Response<AssesmentsResponse>
    @POST("my/update-password")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<Void>
    @Multipart
    @POST("my/profile/{guardian_id}")
    suspend fun updateProfile(
        @Path("guardian_id") guardianId: String,
        @Part file: MultipartBody.Part?,
        @Part("guardian_name") guardianName: RequestBody,
        @Part("relationship_with_child") relationshipWithChild: RequestBody,
        @Part("guardian_birth_date") guardianBirthDate: RequestBody,
        @Part("guardian_phone") guardianPhone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("guardian_occupation") guardianOccupation: RequestBody
    ): Response<Void>
}