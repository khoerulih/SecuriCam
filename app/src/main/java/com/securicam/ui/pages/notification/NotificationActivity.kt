package com.securicam.ui.pages.notification

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.securicam.data.responses.ListNotification
import com.securicam.databinding.ActivityNotificationBinding
import com.securicam.ui.ViewModelFactory
import com.securicam.utils.UserPreference
import com.securicam.utils.UserPreferenceViewModel
import com.securicam.utils.goToLoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class NotificationActivity : AppCompatActivity() {

    private var _binding: ActivityNotificationBinding? = null
    private val binding get() = _binding

    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = UserPreference.getInstance(dataStore)
        val userPreferenceViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application, pref)
            )[UserPreferenceViewModel::class.java]

        val notificationViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[NotificationViewModel::class.java]

        userPreferenceViewModel.getToken().observe(this) { token ->
            if (token.isNullOrEmpty()) {
                goToLoginActivity(this)
                finish()
            } else {
                notificationViewModel.getAllNotification(token)
                accessToken = token
            }
        }
        notificationViewModel.listNotification.observe(this) { data ->
            setListNotification(data)
        }

        notificationViewModel.isLoading.observe(this){
            showLoading(it)
        }

        notificationViewModel.isEmpty.observe(this){
            showDataEmptyMessage(it)
        }

        binding?.rvNotification?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding?.rvNotification?.layoutManager = layoutManager

        supportActionBar?.title = "Notification"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setListNotification(listNotification: List<ListNotification>) {
        val notifications = ArrayList<ListNotification>()
        for (notification in listNotification) {
            val list = ListNotification(
                notification.id,
                notification.imagePath,
                notification.description,
                notification.data,
                notification.email,
                notification.username

            )
            notifications.add(list)
        }
        val adapter = NotificationAdapter(notifications)
        binding?.rvNotification?.adapter = adapter
    }

    private fun showDataEmptyMessage(isEmpty: Boolean) {
        if (isEmpty) {
            binding?.tvEmpty?.visibility = View.VISIBLE
        } else {
            binding?.tvEmpty?.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    companion object {
        fun notificationActivityIntent(context: Context): Intent {
            return Intent(context, NotificationActivity::class.java)
        }
    }
}