package com.securicam.ui.pages.requestpair

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.CameraConnectionResponse
import com.securicam.data.responses.ListConnection
import com.securicam.data.responses.ListRequestPair
import com.securicam.data.responses.PairingRequestResponse
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestPairViewModel: ViewModel() {
    private val _listRequestPair = MutableLiveData<List<ListRequestPair>>()
    val listRequestPair: LiveData<List<ListRequestPair>> = _listRequestPair

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListRequestPair(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPairingRequest("Bearer $token", token)
        client.enqueue(object : Callback<PairingRequestResponse> {
            override fun onResponse(call: Call<PairingRequestResponse>, response: Response<PairingRequestResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listRequestPair.value = response.body()?.listRequestPair as List<ListRequestPair>
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PairingRequestResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "RequestPairViewModel"
    }
}