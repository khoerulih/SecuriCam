package com.securicam.ui.pages.detailconection

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.securicam.R
import com.securicam.data.responses.ListConnection
import com.securicam.data.responses.ListRequestPair
import com.securicam.databinding.ActivityDetailConnectionBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.ui.pages.detailrequestpair.DetailRequestPairActivity
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DetailConnectionActivity : AppCompatActivity() {

    private var _binding: ActivityDetailConnectionBinding? = null
    private val binding get() = _binding

    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailConnectionBinding.inflate(layoutInflater)
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

        val dataConnection = intent.getParcelableExtra<ListConnection>(DetailConnectionActivity.EXTRA_DATA_CONNECTION)

        binding?.tvDetailUsername?.text = dataConnection?.connectionDetail?.username
        binding?.tvDetailEmail?.text = dataConnection?.connectionDetail?.email

        supportActionBar?.title = "Detail Connection"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val EXTRA_DATA_CONNECTION = "extra_data_connection"
    }
}