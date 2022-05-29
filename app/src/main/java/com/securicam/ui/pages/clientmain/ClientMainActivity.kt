package com.securicam.ui.pages.clientmain

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.securicam.MainActivity
import com.securicam.R

class ClientMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)
    }

    companion object {
        fun clientMainActivityIntent(context: Context): Intent {
            return Intent(context, ClientMainActivity::class.java)
        }
    }
}