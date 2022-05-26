package com.securicam.ui.pages.register

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.securicam.R
import com.securicam.databinding.ActivityRegisterBinding
import com.securicam.ui.pages.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding

    private var isValidUsername: Boolean? = null
    private var isValidEmail: Boolean? = null
    private var isValidPassword: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupView()
        setButtonEnable()

//        val registerViewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.NewInstanceFactory()
//        )[RegisterViewModel::class.java]
//
//        registerViewModel.isError.observe(this) { error ->
//            showLoading(false)
//            if (error) {
//                Toast.makeText(this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT)
//                    .show()
//
//                goToLoginActivity()
//            }
//        }

        binding.let {
            editTextListener(it?.edtUsername, "username")
            editTextListener(it?.edtEmail, "email")
            editTextListener(it?.edtPassword, "password")
        }

        binding?.tvLogin?.setOnClickListener {
            goToLoginActivity()
        }

        binding?.btnRegister?.setOnClickListener {
            val name = binding?.edtUsername?.text.toString()
            val email = binding?.edtEmail?.text.toString()
            val password = binding?.edtPassword?.text.toString()

            Toast.makeText(this@RegisterActivity, "$name :$email : $password", Toast.LENGTH_SHORT)
                .show()

//            registerViewModel.register(name, email, password)
//            showLoading(true)
        }

//        binding?.ivSetting?.setOnClickListener {
//            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
//        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setButtonEnable() {
        binding?.btnRegister?.let {
            it.isEnabled =
                isValidUsername == true && isValidEmail == true && isValidPassword == true
        }
    }

    private fun editTextListener(edt: EditText?, tag: String) {
        edt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do Nothing
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                when (tag) {
                    "email" -> {
                        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                        isValidEmail = if (s.isNotEmpty()) {
                            s.trim { it <= ' ' }.matches(emailPattern.toRegex())
                        } else {
                            false
                        }
                        setButtonEnable()
                    }
                    "password" -> {
                        isValidPassword = if (s.isNotEmpty()) {
                            s.length >= 6
                        } else {
                            false
                        }
                        setButtonEnable()
                    }
                    "username" -> {
                        isValidUsername = s.isNotEmpty()
                        setButtonEnable()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do Nothing
            }
        })
    }

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding?.progressBar?.visibility = View.VISIBLE
//        } else {
//            binding?.progressBar?.visibility = View.GONE
//        }
//    }

    private fun goToLoginActivity() {
        val intent = LoginActivity.loginActivityIntent(this)
        startActivity(intent)
        finish()
    }

    companion object {
        fun registerActivityIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }
}