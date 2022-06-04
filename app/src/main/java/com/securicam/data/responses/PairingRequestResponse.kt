package com.securicam.data.responses

import com.google.gson.annotations.SerializedName

data class PairingRequestResponse(

	@field:SerializedName("data")
	val listRequestPair: List<ListRequestPair>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ClientDetail(

	@field:SerializedName("fcm")
	val fcm: Any,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("lastLoggedIn")
	val lastLoggedIn: Long,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)

data class CamDetail(

	@field:SerializedName("fcm")
	val fcm: Any,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("lastLoggedIn")
	val lastLoggedIn: Long,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)

data class ListRequestPair(

	@field:SerializedName("senderId")
	val senderId: String,

	@field:SerializedName("accepted")
	val accepted: Boolean,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("time")
	val time: Long,

	@field:SerializedName("recieverId")
	val recieverId: String,

	@field:SerializedName("clientDetail")
	val clientDetail: ClientDetail,

	@field:SerializedName("camDetail")
	val camDetail: CamDetail
)
