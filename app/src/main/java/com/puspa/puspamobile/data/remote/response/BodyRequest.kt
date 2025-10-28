package com.puspa.puspamobile.data.remote.response

data class ChangePasswordRequest(
	val current_password: String,
	val password: String,
	val password_confirmation: String,
	val _method: String = "PUT"
)