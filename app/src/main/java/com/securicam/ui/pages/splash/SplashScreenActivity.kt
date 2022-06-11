package com.securicam.ui.pages.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.securicam.databinding.ActivitySplashScreenBinding
import com.securicam.ui.pages.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashScreenBinding
    private val handler = Handler(Looper.getMainLooper())

    private var valueDelay = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        splashBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        handler.postDelayed({
            val splashIntent = Intent(this, LoginActivity::class.java)
            startActivity(splashIntent)
            finish()
        }, valueDelay)
    }
}