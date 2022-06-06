package com.securicam.data.responses

import com.google.gson.annotations.SerializedName

data class SearchCameraResponse(
    @field:SerializedName("data")
    val listCamera: List<ListCamera>,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ListCamera (
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String

    )