package com.puspa.puspamobile.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ChildResponse(

	@field:SerializedName("data")
	val data: List<ChildData?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class ChildData(

	@field:SerializedName("child_gender")
	val childGender: String? = null,

	@field:SerializedName("child_id")
	val childId: String? = null,

	@field:SerializedName("child_age")
	val childAge: String? = null,

	@field:SerializedName("child_birth_date")
	val childBirthDate: String? = null,

	@field:SerializedName("child_school")
	val childSchool: String? = null,

	@field:SerializedName("child_name")
	val childName: String? = null
) : Parcelable
