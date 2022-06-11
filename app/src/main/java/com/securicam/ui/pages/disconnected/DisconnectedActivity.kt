package com.securicam.ui.pages.disconnected

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.securicam.data.responses.ListConnection
import com.securicam.databinding.ActivityDisconnectedBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.ui.pages.requestconnection.RequestConnectToCamActivity
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DisconnectedActivity : AppCompatActivity() {
    private var _binding: ActivityDisconnectedBinding? = null
    private val binding get() = _binding
    private lateinit var accessToken: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDisconnectedBinding.inflate(layoutInflater)
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

        val dataConnection = intent.getParcelableExtra<ListConnection>(DisconnectedActivity.EXTRA_DATA_DISCONNECT)

        /* binding?.tvDetailUsername?.text = dataCamera?.connectionDetail?.username
         binding?.tvDetailEmail?.text = dataCamera?.connectionDetail?.email
         */
        binding?.tvCamDevice?.text = dataConnection?.connectionDetail?.username

        supportActionBar?.title = "Connected Request"
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
        const val EXTRA_DATA_DISCONNECT = "extra_data_disconnect"

        fun disconnectActivityIntent(context: Context): Intent {
            return Intent(context, DisconnectedActivity::class.java)
        }

    }
}