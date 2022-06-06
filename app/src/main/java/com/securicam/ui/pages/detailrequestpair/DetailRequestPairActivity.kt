package com.securicam.ui.pages.detailrequestpair

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.securicam.R
import com.securicam.data.responses.ListRequestPair
import com.securicam.data.responses.PairingRequestResponse
import com.securicam.databinding.ActivityDetailRequestPairBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.ui.pages.requestpair.RequestPairViewModel
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToCameraMainActivity
import com.securicam.utils.goToLoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailRequestPairActivity : AppCompatActivity() {
    private var _binding: ActivityDetailRequestPairBinding? = null
    private val binding get() = _binding

    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailRequestPairBinding.inflate(layoutInflater)
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

        val detailRequestPairViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailRequestPairViewModel::class.java]

        val dataRequestPair = intent.getParcelableExtra<ListRequestPair>(EXTRA_DATA_REQUEST_PAIR)

        binding?.tvDetailUsername?.text = dataRequestPair?.clientDetail?.username
        binding?.tvDetailEmail?.text = dataRequestPair?.clientDetail?.email
        binding?.btnAccept?.setOnClickListener {
            dataRequestPair?.let {
                detailRequestPairViewModel.acceptPairRequest(
                    accessToken,
                    it.id
                )
            }
        }

        detailRequestPairViewModel.acceptPairRequest.observe(this) { result ->
            if (result.success) {
                goToCameraMainActivity(this)
            }
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.title = "Detail Request Pair"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val EXTRA_DATA_REQUEST_PAIR = "extra_data_request_pair"
    }
}