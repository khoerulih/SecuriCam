package com.securicam.ui.pages.clientmain

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.securicam.R

class RequestConnectToCamActivity : AppCompatActivity() {
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_connect_to_cam)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val usernameTag = resources.getString(R.string.name, username)
    }

    companion object {
        const val TAG = "DetailActivity"
        const val EXTRA_USERNAME = "extra_username"

    }
}