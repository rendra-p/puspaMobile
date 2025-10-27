package com.puspa.puspamobile.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ProfileResponse(

	@field:SerializedName("data")
	val data: ProfileData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class ProfileData(

	@field:SerializedName("family_id")
	val familyId: String? = null,

	@field:SerializedName("guardian_name")
	val guardianName: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("guardian_occupation")
	val guardianOccupation: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("guardian_type")
	val guardianType: String? = null,

	@field:SerializedName("guardian_id")
	val guardianId: String? = null,

	@field:SerializedName("guardian_birth_date")
	val guardianBirthDate: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("relationship_with_child")
	val relationshipWithChild: String? = null,

	@field:SerializedName("guardian_phone")
	val guardianPhone: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
