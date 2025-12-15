package com.puspa.puspamobile.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssessmentsResponse(

	@field:SerializedName("data")
	val data: List<AssessmentsData?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class Report(

	@field:SerializedName("uploaded_at")
	val uploadedAt: String? = null,

	@field:SerializedName("available")
	val available: Boolean? = null,

	@field:SerializedName("download_url")
	val downloadUrl: String? = null
) : Parcelable

@Parcelize
data class AssessmentsData(

	@field:SerializedName("child_gender")
	val childGender: String? = null,

	@field:SerializedName("family_id")
	val familyId: String? = null,

	@field:SerializedName("child_age")
	val childAge: String? = null,

	@field:SerializedName("child_birth_info")
	val childBirthInfo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("assessment_id")
	val assessmentId: String? = null,

	@field:SerializedName("scheduled_date")
	val scheduledDate: String? = null,

	@field:SerializedName("child_name")
	val childName: String? = null,

	@field:SerializedName("child_id")
	val childId: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("child_school")
	val childSchool: String? = null,

	@field:SerializedName("report")
	val report: Report? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
