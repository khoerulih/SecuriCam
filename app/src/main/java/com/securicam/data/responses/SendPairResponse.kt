package com.securicam.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SendPairResponse (

    @field:SerializedName("data")
    val listRequestPair: ClientDetail,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class ClientDetail(

    @field:SerializedName("fcm")
    val fcm: String? = null,

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
) : Parcelable

