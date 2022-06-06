package com.securicam.ui.pages.detailrequestpair

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.AcceptPairRequestResponse
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRequestPairViewModel: ViewModel() {

    private val _acceptPairRequest = MutableLiveData<AcceptPairRequestResponse>()
    val acceptPairRequest: LiveData<AcceptPairRequestResponse> = _acceptPairRequest

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
        private const val TAG = "DetailRequestPairVM"
    }
}