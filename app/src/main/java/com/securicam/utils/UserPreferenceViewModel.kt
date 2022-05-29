package com.securicam.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserPreferenceViewModel(private val pref: UserPreference): ViewModel() {
    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

    fun getRole(): LiveData<String> {
        return pref.getRole().asLiveData()
    }

    fun setRole(role: String) {
        viewModelScope.launch {
            pref.setRole(role)
        }
    }

    fun deleteToken(){
        viewModelScope.launch {
            pref.saveToken("")
        }
    }

    fun deleteRole(){
        viewModelScope.launch {
            pref.setRole("")
        }
    }
}