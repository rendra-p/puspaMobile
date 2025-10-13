package com.puspa.puspamobile.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterResponse(

    @field:SerializedName("data")
    val data: RegisterData? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("errors")
    val errors: Errors? = null
) : Parcelable

@Parcelize
data class RegisterData(

    @field:SerializedName("user_id")
    val userId: Int? = null
) : Parcelable

@Parcelize
data class Errors(

    @field:SerializedName("username")
    val username: List<String>? = null,

    @field:SerializedName("email")
    val email: List<String>? = null
) : Parcelable

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)
