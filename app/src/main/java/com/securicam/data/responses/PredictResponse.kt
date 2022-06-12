package com.securicam.data.responses

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("data")
	val dataPredict: List<DataPredict>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataPredict(

	@field:SerializedName("ymin")
	val ymin: Int,

	@field:SerializedName("xmin")
	val xmin: Int,

	@field:SerializedName("ymax")
	val ymax: Int,

	@field:SerializedName("xmax")
	val xmax: Int,

	@field:SerializedName("confidence")
	val confidence: Double,

	@field:SerializedName("label")
	val label: String
)