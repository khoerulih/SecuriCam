package com.securicam.ui.pages.requestconnection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securicam.data.responses.ClientDetail
import com.securicam.data.responses.SendPairResponse
import com.securicam.data.retrofit.ApiConfig
import com.securicam.ui.pages.login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestConnectViewModel : ViewModel() {

    private val _sendResult = MutableLiveData<ClientDetail>()
    val sendResult: LiveData<ClientDetail> = _sendResult

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun sendPairRequest(receiver : String){
        val client = ApiConfig.getApiService().sendPairRequest(receiver)
        client.enqueue(object : Callback<SendPairResponse> {
            override fun onResponse(call: Call<SendPairResponse>, response: Response<SendPairResponse>) {
                if (response.isSuccessful) {
                    _sendResult.value = response.body()?.listRequestPair
                    _isError.value = false
                } else {
                    _isError.value = true
                    Log.e(RequestConnectViewModel.TAG, "RequestFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SendPairResponse>, t: Throwable) {
                _isError.value = true
                Log.e(RequestConnectViewModel.TAG, "onFailure : ${t.message}")
            }

        })
    }


    companion object {
        private const val TAG = "RequestConnectVM"
    }
}