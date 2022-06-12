package com.securicam.ui.pages.notification

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.ListNotification
import com.securicam.data.responses.NotificationResponse
import com.securicam.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel: ViewModel() {
    private val _listNotification = MutableLiveData<List<ListNotification>>()
    val listNotification: LiveData<List<ListNotification>> = _listNotification

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun getAllNotification(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllNotification("Bearer $token", token)
        client.enqueue(object : Callback<NotificationResponse> {
            override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listNotification.value = response.body()?.listNotification as List<ListNotification>
                    _isEmpty.value = _listNotification.value.isNullOrEmpty()
                } else {
                    _isLoading.value = false
                    _isEmpty.value = true
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "NotificationVM"
    }
}