package com.securicam.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class SearchCameraResponse(
	@field:SerializedName("items")
	val items: List<LoginData>
)

data class LoginResponse(

	@field:SerializedName("data")
	val loginData: LoginData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class LoginData(

	/*@field:SerializedName("fcm")
	val fcm: Any,
*/
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("role")
	val role: String,

/*	@field:SerializedName("connection")
	val connection: List<Any?>? = null,*/

	@field:SerializedName("accessToken")
	val accessToken: String

) : Parcelable
