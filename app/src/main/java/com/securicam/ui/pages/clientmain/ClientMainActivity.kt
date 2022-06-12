package com.securicam.ui.pages.clientmain

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.securicam.R
import com.securicam.data.responses.ListCamera
import com.securicam.data.responses.ListConnection
import com.securicam.databinding.ActivityClientMainBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.ui.pages.clientdetail.ClientDetailActivity
import com.securicam.utils.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ClientMainActivity : AppCompatActivity() {

    private var _binding: ActivityClientMainBinding? = null
    private val binding get() = _binding

    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityClientMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = UserPreference.getInstance(dataStore)
        val userPreferenceViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application, pref)
            )[UserPreferenceViewModel::class.java]

        val clientMainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ClientMainViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this) { token ->
            if (token.isNullOrEmpty()) {
                goToLoginActivity(this)
                finish()
            } else {
                clientMainViewModel.getAllCameraConnection(token)
                accessToken = token
            }
        }

        clientMainViewModel.listConnection.observe(this) { data ->
            setListCamera(data)
        }

        clientMainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        clientMainViewModel.isEmpty.observe(this){
            showDataEmptyMessage(it)
        }

        binding?.rvConnection?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding?.rvConnection?.layoutManager = layoutManager

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_device -> {
                goToSearchCamActivity(this)
                return true
            }

            R.id.notification -> {
                goToNotificationActivity(this)
                return true
            }

            R.id.logout -> {
                val pref = UserPreference.getInstance(dataStore)
                val userPreferenceViewModel =
                    ViewModelProvider(
                        this,
                        ViewModelFactory.getInstance(application, pref)
                    )[UserPreferenceViewModel::class.java]
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.logout))
                    .setMessage(getString(R.string.logout_confirmation))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        goToLoginActivity(this)
                        userPreferenceViewModel.deleteToken()
                        userPreferenceViewModel.deleteRole()
                        Toast.makeText(this, getString(R.string.logout_success), Toast.LENGTH_LONG)
                            .show()
                        finish()
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                        // Do Nothing
                    }
                    .show()
                return true
            }
            else -> return true
        }
    }

    private fun setListCamera(listConnection: List<ListConnection>) {
        val connections = ArrayList<ListConnection>()
        for (connection in listConnection) {
            val list = ListConnection(
                connection.time,
                connection.connectionDetail
            )
            connections.add(list)
        }
        val adapter = ListClientConnectionAdapter(connections)
        binding?.rvConnection?.adapter = adapter
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
        fun clientMainActivityIntent(context: Context): Intent {
            return Intent(context, ClientMainActivity::class.java)
        }
    }
}