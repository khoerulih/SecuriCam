package com.securicam.ui.pages.clientmain

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
import com.securicam.R
import com.securicam.ui.ViewModelFactory
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ClientMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)

        val pref = UserPreference.getInstance(dataStore)
        val userPreferenceViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application, pref)
            )[UserPreferenceViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this) { token ->
            if(token.isNullOrEmpty()){
                goToLoginActivity(this)
                finish()
            }
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

    companion object {
        fun clientMainActivityIntent(context: Context): Intent {
            return Intent(context, ClientMainActivity::class.java)
        }
    }
}