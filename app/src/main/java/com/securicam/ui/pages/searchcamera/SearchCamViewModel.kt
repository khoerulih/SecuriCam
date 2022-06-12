package com.securicam.ui.pages.searchcamera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.CameraConnectionResponse
import com.securicam.data.responses.ListCamera
import com.securicam.data.responses.ListConnection
import com.securicam.data.responses.SearchCameraResponse
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchCamViewModel: ViewModel() {
    private val _listCamera = MutableLiveData<List<ListCamera>>()
    val listCamera: LiveData<List<ListCamera>> = _listCamera

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun searchCamera(token: String,username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().search("Bearer $token", token, username)
        client.enqueue(object : Callback<SearchCameraResponse> {
            override fun onResponse(
                call: Call<SearchCameraResponse>,
                response: Response<SearchCameraResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listCamera.value = response.body()?.listCamera as List<ListCamera>
                    _isEmpty.value = _listCamera.value.isNullOrEmpty()
                } else {
                    _isEmpty.value = true
                    _isLoading.value = false
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchCameraResponse>, t: Throwable) {
                _isEmpty.value = true
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }


    companion object {
        private const val TAG = "SearchCamViewModel"
    }
}