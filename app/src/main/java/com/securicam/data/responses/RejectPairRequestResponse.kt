package com.securicam.data.responses

import com.google.gson.annotations.SerializedName

data class RejectPairRequestResponse(
    @field:SerializedName("data")
    val data: Any? = null,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)
