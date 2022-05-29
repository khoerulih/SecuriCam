package com.securicam.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val key = stringPreferencesKey("user_token")
    private val userRole = stringPreferencesKey("user_role")

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: ""
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[key] = token
        }
    }

    fun getRole(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[userRole] ?: ""
        }
    }

    suspend fun setRole(role: String) {
        dataStore.edit { preferences ->
            preferences[userRole] = role
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}