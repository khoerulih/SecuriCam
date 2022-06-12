package com.securicam.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NotificationResponse(
    @field:SerializedName("data")
    val listNotification: List<ListNotification>,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class ListNotification(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("from")
    val from: String,

    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("to")
    val to: String,

    @field:SerializedName("imagePath")
    val imagePath: String? = null,

    @field:SerializedName("time")
    val time: Long

) : Parcelable
