package com.securicam.ui.pages.detailconection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.AcceptPairRequestResponse
import com.securicam.data.responses.DeleteConnectionResponse
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailConnectionViewModel: ViewModel() {

    private val _deleteConnection = MutableLiveData<DeleteConnectionResponse>()
    val deleteConnection: LiveData<DeleteConnectionResponse> = _deleteConnection

    fun deleteClientConnection(token: String, id: String) {
        val client = ApiConfig.getApiService().deleteConnection("Bearer $token", token, id)
        client.enqueue(object : Callback<DeleteConnectionResponse> {
            override fun onResponse(call: Call<DeleteConnectionResponse>, response: Response<DeleteConnectionResponse>) {
                if (response.isSuccessful) {
                    _deleteConnection.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DeleteConnectionResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailRequestPairVM"
    }
}