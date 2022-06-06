package com.securicam.ui.pages.clientmain

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.securicam.data.responses.ListCamera
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

        val cameraViewModel1 = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[CameraSearchViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this){ token ->
            if(token.isNullOrEmpty()){
                goToLoginActivity(this)
            } else {
                cameraViewModel1.getAllCameraConnection(token)
            }
        }

        binding?.rvDevice?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding?.rvDevice?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvDevice?.addItemDecoration(itemDecoration)


        cameraViewModel1.listConnection.observe(this) { listCameraConnection ->
            setListCameraConnection(listCameraConnection)
        }

        cameraViewModel1.listCamera.observe(this) { listCamera ->
            setListCamera(listCamera)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val pref = UserPreference.getInstance(dataStore)
        val userPreferenceViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application, pref)
            )[UserPreferenceViewModel::class.java]

        val cameraViewModel1 = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[CameraSearchViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this){ token ->
            if(token.isNullOrEmpty()){
                goToLoginActivity(this)
            } else {
                cameraViewModel1.getAllCameraConnection(token)
            }
        }

        val inflater = menuInflater
        inflater.inflate(R.menu.device_option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.ic_device).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint_text)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                cameraViewModel1.searchCamera(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }
        })

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.ic_device -> {
                return true
            }

            else -> throw IllegalArgumentException("Unknown menu item: " + item.itemId)
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
        val adapter = CameraSearchAdapter(connections)
        binding?.rvDevice?.adapter = adapter
    }

}