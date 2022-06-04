package com.securicam.ui.pages.clientmain

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.LoginData
import com.securicam.data.responses.SearchCameraResponse
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraSearchViewModel: ViewModel() {
    private val _listCamera = MutableLiveData<List<LoginData>>()
    val listUser: LiveData<List<LoginData>> = _listCamera

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDataError = MutableLiveData<Boolean>()
    val isDataError: LiveData<Boolean> = _isDataError

    init {
        showListCamera()
    }

    fun showListCamera() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<LoginData>> {
            override fun onResponse(
                call: Call<List<LoginData>>,
                response: Response<List<LoginData>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isDataError.value = false
                    _listCamera.value = response.body()
                } else {
                    _isDataError.value = true
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<LoginData>>, t: Throwable) {
                _isLoading.value = false
                _isDataError.value = true
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
                if (response.isSuccessful) {
                    _listCamera.value = response.body()?.items
                    _isDataError.value = _listCamera.value.isNullOrEmpty()
                } else {
                    _isDataError.value = true
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchCameraResponse>, t: Throwable) {
                _isLoading.value = false
                _isDataError.value = true
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }


    companion object {
        private const val TAG = "CameraSearchViewModel"
    }
}