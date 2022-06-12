package com.securicam.ui.pages.requestconnection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.ClientDetail2
import com.securicam.data.responses.SendPairResponse
import com.securicam.data.retrofit.ApiConfig
import com.securicam.ui.pages.login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestConnectViewModel : ViewModel() {

    private val _sendPairResult = MutableLiveData<SendPairResponse>()
    val sendPairResult: LiveData<SendPairResponse> = _sendPairResult

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun sendPairRequest(token: String, receiver: String){
        val client = ApiConfig.getApiService().sendPairRequest("Bearer $token", token, receiver)
        client.enqueue(object : Callback<SendPairResponse> {
            override fun onResponse(call: Call<SendPairResponse>, response: Response<SendPairResponse>) {
                if (response.isSuccessful) {
                    _sendPairResult.value = response.body()
                    _isError.value = false
                } else {
                    _isError.value = true
                    Log.e(TAG, "RequestFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SendPairResponse>, t: Throwable) {
                _isError.value = true
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "RequestConnectVM"
    }
}