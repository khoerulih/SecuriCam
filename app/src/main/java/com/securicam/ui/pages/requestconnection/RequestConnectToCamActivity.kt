package com.securicam.ui.pages.requestconnection

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
import com.securicam.databinding.ActivityRequestConnectToCamBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class RequestConnectToCamActivity : AppCompatActivity() {
    private var _binding: ActivityRequestConnectToCamBinding? = null
    private val binding get() = _binding
    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRequestConnectToCamBinding.inflate(layoutInflater)
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

        val requestConnectViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[RequestConnectViewModel::class.java]

        requestConnectViewModel.isError.observe(this) { error ->
            showLoading(false)
            if (error) {
                Toast.makeText(this, getString(R.string.request_failed), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.request_success), Toast.LENGTH_SHORT)
                    .show()

                goToLoginActivity(this)
                finish()
            }
        }


        val dataCamera = intent.getParcelableExtra<ListCamera>(EXTRA_DATA_SEND_PAIR)

        /* binding?.tvDetailUsername?.text = dataCamera?.connectionDetail?.username
         binding?.tvDetailEmail?.text = dataCamera?.connectionDetail?.email
         */
        binding?.tvCamDevice?.text = dataCamera?.username
        binding?.tvEmailCam?.text = dataCamera?.email
        binding?.tvId?.text = dataCamera?.id

        binding?.btnRequest?.setOnClickListener{
            val receiver = binding?.tvId?.text.toString()

            requestConnectViewModel.sendPairRequest(accessToken, receiver)
            showLoading(true)

        }

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

        const val EXTRA_DATA_SEND_PAIR = "extra_data_send_pair"

        fun requestConnectToCamActivityIntent(context: Context): Intent {
            return Intent(context, RequestConnectToCamActivity::class.java)
        }
    }
}
