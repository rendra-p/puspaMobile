package com.puspa.puspamobile.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ChildDetailResponse(

	@field:SerializedName("data")
	val data: ChildDetailData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class ChildDetailData(

	@field:SerializedName("child_gender")
	val childGender: String? = null,

	@field:SerializedName("guardian_name")
	val guardianName: String? = null,

	@field:SerializedName("child_age")
	val childAge: String? = null,

	@field:SerializedName("father_identity_number")
	val fatherIdentityNumber: String? = null,

	@field:SerializedName("father_relationship")
	val fatherRelationship: String? = null,

	@field:SerializedName("mother_name")
	val motherName: String? = null,

	@field:SerializedName("mother_phone")
	val motherPhone: String? = null,

	@field:SerializedName("child_birth_info")
	val childBirthInfo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("guardian_identity_number")
	val guardianIdentityNumber: String? = null,

	@field:SerializedName("mother_occupation")
	val motherOccupation: String? = null,

	@field:SerializedName("child_name")
	val childName: String? = null,

	@field:SerializedName("mother_age")
	val motherAge: String? = null,

	@field:SerializedName("guardian_relationship")
	val guardianRelationship: String? = null,

	@field:SerializedName("child_complaint")
	val childComplaint: String? = null,

	@field:SerializedName("father_age")
	val fatherAge: String? = null,

	@field:SerializedName("child_address")
	val childAddress: String? = null,

	@field:SerializedName("father_phone")
	val fatherPhone: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("child_school")
	val childSchool: String? = null,

	@field:SerializedName("guardian_age")
	val guardianAge: String? = null,

	@field:SerializedName("father_occupation")
	val fatherOccupation: String? = null,

	@field:SerializedName("guardian_occupation")
	val guardianOccupation: String? = null,

	@field:SerializedName("child_service_choice")
	val childServiceChoice: String? = null,

	@field:SerializedName("mother_identity_number")
	val motherIdentityNumber: String? = null,

	@field:SerializedName("mother_relationship")
	val motherRelationship: String? = null,

	@field:SerializedName("father_name")
	val fatherName: String? = null,

	@field:SerializedName("child_religion")
	val childReligion: String? = null,

	@field:SerializedName("guardian_phone")
	val guardianPhone: String? = null
) : Parcelable
