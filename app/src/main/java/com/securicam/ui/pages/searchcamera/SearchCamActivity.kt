package com.securicam.ui.pages.searchcamera

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.securicam.R
import com.securicam.data.responses.ListCamera
import com.securicam.databinding.ActivitySearchCamBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SearchCamActivity : AppCompatActivity() {

    private var _binding: ActivitySearchCamBinding? = null
    private val binding get() = _binding

    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchCamBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = UserPreference.getInstance(dataStore)
        val userPreferenceViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application, pref)
            )[UserPreferenceViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this){ token ->
            if(token.isNullOrEmpty()){
                goToLoginActivity(this)
            } else {
                accessToken = token
            }
        }

        val searchCamViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SearchCamViewModel::class.java]

        searchCamViewModel.listCamera.observe(this){ response ->
            setListCamera(response)
        }

        searchCamViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        searchCamViewModel.isEmpty.observe(this) {
            showDataEmptyMessage(it)
        }

        binding?.rvSearch?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding?.rvSearch?.layoutManager = layoutManager

        supportActionBar?.title = "Search Camera"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val searchCamViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SearchCamViewModel::class.java]

        val inflater = menuInflater
        inflater.inflate(R.menu.device_option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.ic_device).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint_text)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchCamViewModel.searchCamera(accessToken, query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    private fun setListCamera(listCamera: List<ListCamera>) {
        val connections = ArrayList<ListCamera>()
        for (connection in listCamera) {
            val list = ListCamera(
                connection.id,
                connection.email,
                connection.username
            )
            connections.add(list)
        }
        val adapter = ListSearchCamAdapter(connections)
        binding?.rvSearch?.adapter = adapter
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
            binding?.tvEmpty?.text = "No result found with that keyword"
        } else {
            binding?.tvEmpty?.visibility = View.GONE
        }
    }

    companion object {
        fun searchCamActivityIntent(context: Context): Intent {
            return Intent(context, SearchCamActivity::class.java)
        }
    }
}