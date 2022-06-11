package com.securicam.ui.pages.clientdetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.securicam.R
import com.securicam.data.responses.ListCamera
import com.securicam.data.responses.LoginData
import com.securicam.databinding.ActivityClientDetailBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ClientDetailActivity : AppCompatActivity() {
    private var _binding: ActivityClientDetailBinding? = null
    private val binding get() = _binding
    private lateinit var accessToken: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityClientDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val pref = UserPreference.getInstance(dataStore)
        val userPreferenceViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application, pref)
            )[UserPreferenceViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this) { token ->
            if (token.isNullOrEmpty()) {
                goToLoginActivity(this)
            } else {
                accessToken = token
            }
        }


    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_DATA_USER = "extra_data_user"
    }
}