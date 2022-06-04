package com.securicam.ui.pages.clientmain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.securicam.R

class DetailClientNotification : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_client_notification)

    }

    override fun onBackPressed() {
            super.onBackPressed()
            return
    }
}