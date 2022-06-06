package com.securicam.ui.pages.requestpair

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.*
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestPairViewModel: ViewModel() {
    private val _listRequestPair = MutableLiveData<List<ListRequestPair>>()
    val listRequestPair: LiveData<List<ListRequestPair>> = _listRequestPair

    private val _acceptPairRequest = MutableLiveData<AcceptPairRequestResponse>()
    val acceptPairRequest: LiveData<AcceptPairRequestResponse> = _acceptPairRequest

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

    fun acceptPairRequest(token: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().acceptPairRequest("Bearer $token", token, id)
        client.enqueue(object : Callback<AcceptPairRequestResponse> {
            override fun onResponse(call: Call<AcceptPairRequestResponse>, response: Response<AcceptPairRequestResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _acceptPairRequest.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AcceptPairRequestResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "RequestPairViewModel"
    }
}