package com.securicam.ui.pages.cameramain

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.securicam.R
import com.securicam.data.responses.ListConnection
import com.securicam.databinding.ActivityCameraMainBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.ui.pages.login.LoginActivity
import com.securicam.ui.pages.observe.ObserveActivity
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity
import com.securicam.utils.goToRequestPairActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class CameraMainActivity : AppCompatActivity() {

    private var _binding: ActivityCameraMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCameraMainBinding.inflate(layoutInflater)
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
        )[CameraViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this){ token ->
            if(token.isNullOrEmpty()){
                goToLoginActivity(this)
            } else {
                cameraViewModel.getAllCameraConnection(token)
            }
        }

        binding?.rvConnection?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvConnection?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvConnection?.addItemDecoration(itemDecoration)

        cameraViewModel.listConnection.observe(this) { listCameraConnection ->
            setListCameraConnection(listCameraConnection)
        }

        cameraViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        cameraViewModel.isEmpty.observe(this) {
            showDataEmptyMessage(it)
        }

        binding?.fabStartObserve?.setOnClickListener {
            val intent = Intent(this, ObserveActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.cam_option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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
                        val intent = LoginActivity.loginActivityIntent(this)
                        startActivity(intent)
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
            R.id.request -> {
                goToRequestPairActivity(this)
                return true
            }
            else -> return true
        }
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
        val adapter = ListConnectionAdapter(connections)
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
        fun cameraMainActivityIntent(context: Context): Intent {
            return Intent(context, CameraMainActivity::class.java)
        }
    }
}