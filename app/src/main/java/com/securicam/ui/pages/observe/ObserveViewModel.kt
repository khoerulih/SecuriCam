package com.securicam.ui.pages.observe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.PredictResponse
import com.securicam.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ObserveViewModel: ViewModel() {

    private val _sendImageResponse = MutableLiveData<PredictResponse>()
    val sendImageResponses: LiveData<PredictResponse> = _sendImageResponse

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun sendImageForPredict(token: String, image: MultipartBody.Part) {
        val client = ApiConfig.getApiService().sendImageForPredictRequest("Bearer $token", token, image)
        client.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
                if (response.isSuccessful) {
                    _isError.value = false
                    _sendImageResponse.value = response.body()
                } else {
                    _isError.value = true
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                _isError.value = true
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ObserveViewModel"
    }

}