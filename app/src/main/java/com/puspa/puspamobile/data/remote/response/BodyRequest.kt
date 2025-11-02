package com.puspa.puspamobile.data.remote.response

data class ChangePasswordRequest(
	val current_password: String,
	val password: String,
	val password_confirmation: String,
	val _method: String = "PUT"
)

data class UpdateProfileRequest(
	val guardian_name: String,
	val relationship_with_child: String,
	val guardian_birth_date: String,
	val guardian_phone: String,
	val email: String,
	val guardian_occupation: String,
	val _method: String = "PUT"
)