package com.securicam.ui.pages.cameramain

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.securicam.MainActivity
import com.securicam.R
import com.securicam.databinding.ActivityCameraMainBinding
import com.securicam.databinding.ActivityObserveBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.ui.pages.login.LoginActivity
import com.securicam.ui.pages.observe.ObserveActivity
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class CameraMainActivity : AppCompatActivity() {

    private var _binding: ActivityCameraMainBinding? = null
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCameraMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.fabStartObserve?.setOnClickListener {
            val intent = Intent(this, ObserveActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
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
            else -> return true
        }
    }

    companion object {
        fun cameraMainActivityIntent(context: Context): Intent {
            return Intent(context, CameraMainActivity::class.java)
        }
    }
}