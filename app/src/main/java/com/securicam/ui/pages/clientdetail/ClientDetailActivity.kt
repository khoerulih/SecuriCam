package com.securicam.ui.pages.clientdetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.securicam.R
import com.securicam.data.responses.ListCamera
import com.securicam.data.responses.ListConnection
import com.securicam.data.responses.LoginData
import com.securicam.databinding.ActivityClientDetailBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.ui.pages.detailconection.DetailConnectionActivity
import com.securicam.ui.pages.detailconection.DetailConnectionViewModel
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToClientMainActivity
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

        val clientDetailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ClientDetailViewModel::class.java]


        val dataUser = intent.getParcelableExtra<ListConnection>(EXTRA_DATA_USER)

        binding?.tvCamUsername?.text = dataUser?.connectionDetail?.username
        binding?.tvCamEmail?.text = dataUser?.connectionDetail?.email

        binding?.btnDisconnect?.setOnClickListener {
            dataUser?.connectionDetail?.let { data ->
                clientDetailViewModel.deleteCamConnection(
                    accessToken,
                    data.id
                )
            }
            showLoading(true)
        }

        clientDetailViewModel.deleteConnection.observe(this){ response ->
            showLoading(false)
            if(response.success){
                goToClientMainActivity(this)
            }
            Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.title = "Detail Client Connection"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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