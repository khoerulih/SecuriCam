package com.securicam.ui.pages.cameramain

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.securicam.MainActivity
import com.securicam.R

class CameraMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_main)
    }

    companion object {
        fun cameraMainActivityIntent(context: Context): Intent {
            return Intent(context, CameraMainActivity::class.java)
        }
    }
}