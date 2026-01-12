package com.puspa.puspamobile.data.remote.retrofit

import com.puspa.puspamobile.data.remote.response.AddChildRequest
import com.puspa.puspamobile.data.remote.response.AssessmentsResponse
import com.puspa.puspamobile.data.remote.response.ChangePasswordRequest
import com.puspa.puspamobile.data.remote.response.ChildDetailResponse
import com.puspa.puspamobile.data.remote.response.ChildResponse
import com.puspa.puspamobile.data.remote.response.ForgotPasswordRequest
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.LoginResponse
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.data.remote.response.RegisterResponse
import com.puspa.puspamobile.data.remote.response.ResetPasswordRequest
import com.puspa.puspamobile.data.remote.response.UpdateChildRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Query("token") token: String,
        @Query("email") email: String,
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Response<Void>
    @GET("my/profile")
    suspend fun getProfile(): Response<ProfileResponse>
    @GET("my/children")
    suspend fun getChildren(): Response<ChildResponse>
    @GET("my/children/{child_id}")
    suspend fun getChildrenDetail(
        @Path("child_id") childId: String
    ): Response<ChildDetailResponse>
    @POST("my/children/{child_id}")
    suspend fun updateChild(
        @Path("child_id") childId: String,
        @Body updateChildRequest: UpdateChildRequest
    ): Response<Void>
    @DELETE("my/children/{child_id}")
    suspend fun deleteChild(
        @Path("child_id") childId: String
    ): Response<Void>
    @POST("my/children")
    suspend fun addChild(
        @Body addChildRequest: AddChildRequest
    ): Response<Void>
    @GET("my/assessments")
    suspend fun getAssessments(): Response<AssessmentsResponse>
    @POST("profile/update-password")
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