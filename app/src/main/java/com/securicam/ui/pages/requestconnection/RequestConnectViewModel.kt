package com.securicam.ui.pages.requestconnection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.SendPairResponse
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestConnectViewModel : ViewModel() {

    private val _sendPairRequest = MutableLiveData<SendPairResponse>()
    val sendPairRequest: LiveData<SendPairResponse> = _sendPairRequest

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun sendPairRequest(token: String, receiver: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().sendPairRequest("Bearer $token", token, receiver)
        client.enqueue(object : Callback<SendPairResponse> {
            override fun onResponse(call: Call<SendPairResponse>, response: Response<SendPairResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _sendPairRequest.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SendPairResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailRequestPairVM"
    }
}