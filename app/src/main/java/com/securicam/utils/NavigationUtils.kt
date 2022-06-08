package com.securicam.utils

import android.content.Context
import androidx.core.content.ContextCompat.startActivity
import com.securicam.ui.pages.cameramain.CameraMainActivity
import com.securicam.ui.pages.clientmain.ClientMainActivity
import com.securicam.ui.pages.clientmain.ListDeviceActivity
import com.securicam.ui.pages.login.LoginActivity
import com.securicam.ui.pages.register.RegisterActivity
import com.securicam.ui.pages.sendpairing.RequestConnectToCamActivity

fun goToLoginActivity(context: Context) {
    val intent = LoginActivity.loginActivityIntent(context)
    context.startActivity(intent)
}

fun goToClientMainActivity(context: Context) {
    val intent = ClientMainActivity.clientMainActivityIntent(context)
    context.startActivity(intent)
}

fun goToRequestConnectToCamActivity(context: Context) {
    val intent = RequestConnectToCamActivity.requestConnectToCamActivityIntent(context)
    context.startActivity(intent)
}

fun goToCameraMainActivity(context: Context) {
    val intent = CameraMainActivity.cameraMainActivityIntent(context)
    context.startActivity(intent)
}

fun goToRegisterActivity(context: Context) {
    val intent = RegisterActivity.registerActivityIntent(context)
    context.startActivity(intent)
}