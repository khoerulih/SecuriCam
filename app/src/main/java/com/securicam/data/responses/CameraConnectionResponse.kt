package com.securicam.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class CameraConnectionResponse(
    @field:SerializedName("data")
    val listConnection: List<ListConnection>,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)
@Parcelize
data class ListConnection(

    @field:SerializedName("time")
    val time: Long? = null,

    @field:SerializedName("user")
    val connectionDetail: ConnectionDetail
) : Parcelable

@Parcelize
data class ConnectionDetail(

    /*@field:SerializedName("fcm")
    val fcm: Any,
*/
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