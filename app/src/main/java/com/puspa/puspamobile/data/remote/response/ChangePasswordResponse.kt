package com.puspa.puspamobile.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import retrofit2.http.PUT

@Parcelize
data class ChangePasswordResponse(

	@field:SerializedName("data")
	val data: List<String?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

data class ChangePasswordRequest(
	val current_password: String,
	val password: String,
	val password_confirmation: String,
	val _method: String = "PUT"
)