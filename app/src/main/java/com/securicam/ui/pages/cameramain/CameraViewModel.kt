package com.securicam.ui.pages.cameramain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.CameraConnectionResponse
import com.securicam.data.responses.ListConnection
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel: ViewModel() {
    private val _listConnection = MutableLiveData<List<ListConnection>>()
    val listConnection: LiveData<List<ListConnection>> = _listConnection

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllCameraConnection(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllCameraConnections("Bearer $token", token)
        client.enqueue(object : Callback<CameraConnectionResponse> {
            override fun onResponse(call: Call<CameraConnectionResponse>, response: Response<CameraConnectionResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listConnection.value = response.body()?.listConnection as List<ListConnection>
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CameraConnectionResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "CameraViewModel"
    }
}