package com.puspa.puspamobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(

	@SerializedName("current_password")
	val currentPassword: String,

	@SerializedName("password")
	val password: String,

	@SerializedName("password_confirmation")
	val passwordConfirmation: String,

	@SerializedName("_method")
	val method: String = "PUT"
)

data class UpdateProfileRequest(

	@SerializedName("guardian_name")
	val guardianName: String,

	@SerializedName("relationship_with_child")
	val relationshipWithChild: String,

	@SerializedName("guardian_birth_date")
	val guardianBirthDate: String,

	@SerializedName("guardian_phone")
	val guardianPhone: String,

	@SerializedName("email")
	val email: String,

	@SerializedName("guardian_occupation")
	val guardianOccupation: String,

	@SerializedName("_method")
	val method: String = "PUT"
)

data class AddChildRequest(

	@SerializedName("child_name")
	val childName: String,

	@SerializedName("child_gender")
	val childGender: String,

	@SerializedName("child_birth_place")
	val childBirthPlace: String,

	@SerializedName("child_birth_date")
	val childBirthDate: String,

	@SerializedName("child_school")
	val childSchool: String,

	@SerializedName("child_address")
	val childAddress: String,

	@SerializedName("child_complaint")
	val childComplaint: String,

	@SerializedName("child_service_choice")
	val childServiceChoice: String
)