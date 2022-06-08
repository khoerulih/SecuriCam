package com.securicam.ui.pages.sendpairing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.securicam.R
import com.securicam.ui.pages.clientmain.ClientMainActivity

class RequestConnectToCamActivity : AppCompatActivity() {
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_connect_to_cam)

        supportActionBar?.title = "Connected Request"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {

        const val EXTRA_DATA_SEND_PAIR = "extra_data_send_pair"

        fun requestConnectToCamActivityIntent(context: Context): Intent {
            return Intent(context, RequestConnectToCamActivity::class.java)
        }
    }
}
