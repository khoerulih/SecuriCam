package com.securicam.ui.pages.clientmain

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.*
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraSearchViewModel: ViewModel() {
    private val _listCamera = MutableLiveData<List<ListCamera>>()
    private val _listConnection = MutableLiveData<List<ListConnection>>()

    val listCamera: LiveData<List<ListCamera>> = _listCamera
    val listConnection: LiveData<List<ListConnection>> = _listConnection

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getAllCameraConnection(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers("Bearer $token", token)
        client.enqueue(object : Callback<CameraConnectionResponse> {
            override fun onResponse(call: Call<CameraConnectionResponse>, response: Response<CameraConnectionResponse>) {
                Log.d("response check ", "" + response.body()?.listConnection.toString())
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

    fun searchCamera(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().search(username)
        client.enqueue(object : Callback<SearchCameraResponse> {
            override fun onResponse(
                call: Call<SearchCameraResponse>,
                response: Response<SearchCameraResponse>
            ) {
                _isLoading.value = false
                Log.d("response check ", "" + response.body()?.listCamera.toString())
                if (response.isSuccessful) {
                    _listCamera.value = response.body()?.listCamera as List<ListCamera>
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchCameraResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }


    companion object {
        private const val TAG = "CameraSearchViewModel"
    }
}