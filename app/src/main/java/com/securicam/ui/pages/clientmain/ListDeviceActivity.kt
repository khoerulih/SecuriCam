package com.securicam.ui.pages.clientmain

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.securicam.R
import com.securicam.data.responses.ListConnection
import com.securicam.data.responses.LoginData
import com.securicam.databinding.ActivityListDeviceBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.ui.pages.login.LoginActivity
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ListDeviceActivity : AppCompatActivity() {

    private var _binding : ActivityListDeviceBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListDeviceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = UserPreference.getInstance(dataStore)
        val userPreferenceViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application, pref)
            )[UserPreferenceViewModel::class.java]

        val cameraViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[CameraSearchViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this){ token ->
            if(token.isNullOrEmpty()){
                goToLoginActivity(this)
            } else {
                cameraViewModel.getAllCameraConnection(token)
            }
        }

        binding?.rvDevice?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding?.rvDevice?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvDevice?.addItemDecoration(itemDecoration)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = SearchView(this)
        binding?.searchEditText?.addView(searchView)
        binding?.searchEditText?.visibility = View.VISIBLE

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint_text)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                cameraViewModel.searchCamera(query)
                binding?.searchEditText?.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrBlank()) {
                    cameraViewModel.getAllCameraConnection("Bearer")
                }
                return true
            }
        })

        cameraViewModel.listConnection.observe(this) { listCameraConnection ->
            setListCameraConnection(listCameraConnection)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setListCameraConnection(listConnection : List<ListConnection>) {
        val connections = ArrayList<ListConnection>()
        for (connection in listConnection) {
            val list = ListConnection(
                connection.time,
                connection.connectionDetail
            )
            connections.add(list)
        }
        val adapter = CameraAdapter(connections)
        binding?.rvDevice?.adapter = adapter
    }

}