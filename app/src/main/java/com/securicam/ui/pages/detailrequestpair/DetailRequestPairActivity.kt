package com.securicam.ui.pages.detailrequestpair

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.securicam.data.responses.ListRequestPair
import com.securicam.databinding.ActivityDetailRequestPairBinding
import com.securicam.ui.ViewModelFactory
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
        Log.d("ID", "${dataRequestPair?.clientDetail?.id}")
        binding?.btnAccept?.setOnClickListener {
            dataRequestPair?.let { response ->
                detailRequestPairViewModel.acceptPairRequest(
                    accessToken,
                    response.id
                )
            }
            showLoading(true)
        }

        binding?.btnReject?.setOnClickListener {
            dataRequestPair?.let { response ->
                detailRequestPairViewModel.rejectPairRequest(
                    accessToken,
                    response.id
                )
            }
            showLoading(true)
        }

        detailRequestPairViewModel.acceptPairRequest.observe(this) { result ->
            showLoading(false)
            if (result.success) {
                goToCameraMainActivity(this)
            }
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
        }

        detailRequestPairViewModel.rejectPairRequest.observe(this) { result ->
            showLoading(false)
            if (result.success) {
                goToCameraMainActivity(this)
            }
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.title = "Detail Request Pair"
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
        const val EXTRA_DATA_REQUEST_PAIR = "extra_data_request_pair"
    }
}