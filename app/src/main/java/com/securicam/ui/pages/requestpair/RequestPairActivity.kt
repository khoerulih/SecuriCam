package com.securicam.ui.pages.requestpair

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.securicam.data.responses.ListRequestPair
import com.securicam.databinding.ActivityRequestPairBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class RequestPairActivity : AppCompatActivity() {

    private var _binding: ActivityRequestPairBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRequestPairBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = UserPreference.getInstance(dataStore)
        val userPreferenceViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application, pref)
            )[UserPreferenceViewModel::class.java]

        val requestPairViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[RequestPairViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this){ token ->
            if(token.isNullOrEmpty()){
                goToLoginActivity(this)
            } else {
                requestPairViewModel.getListRequestPair(token)
            }
        }

        binding?.rvRequestPair?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvRequestPair?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvRequestPair?.addItemDecoration(itemDecoration)

        requestPairViewModel.listRequestPair.observe(this) { listRequestPair ->
            setListRequestPair(listRequestPair)
        }

        requestPairViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        requestPairViewModel.isEmpty.observe(this) {
            showDataEmptyMessage(it)
        }


        supportActionBar?.title = "List Request Pairing"
    }

    private fun setListRequestPair(listRequestPair : List<ListRequestPair>) {
        val requestPair = ArrayList<ListRequestPair>()
        for (listRequest in listRequestPair) {
            if(!listRequest.accepted){
                val list = ListRequestPair(
                    listRequest.senderId,
                    listRequest.accepted,
                    listRequest.id,
                    listRequest.time,
                    listRequest.recieverId,
                    listRequest.clientDetail,
                    listRequest.camDetail
                )
                requestPair.add(list)
            }
        }
        val adapter = ListRequestPairAdapter(requestPair)
        binding?.rvRequestPair?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun showDataEmptyMessage(isEmpty: Boolean) {
        if (isEmpty) {
            binding?.tvEmpty?.visibility = View.VISIBLE
        } else {
            binding?.tvEmpty?.visibility = View.GONE
        }
    }

    companion object {
        fun requestPairActivityIntent(context: Context): Intent {
            return Intent(context, RequestPairActivity::class.java)
        }
    }
}