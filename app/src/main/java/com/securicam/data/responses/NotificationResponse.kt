package com.securicam.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NotificationResponse(
    @field:SerializedName("data")
    val listNotification: ListNotification,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)
@Parcelize
data class ListNotification(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("imagePath")
    val imagePath: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("data")
    val data: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String,

) : Parcelable
