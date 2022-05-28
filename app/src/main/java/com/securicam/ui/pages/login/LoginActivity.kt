package com.securicam.ui.pages.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import com.securicam.MainActivity
import com.securicam.R
import com.securicam.databinding.ActivityLoginBinding
import com.securicam.ui.pages.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding

    private var isValidEmail: Boolean? = null
    private var isValidPassword: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupView()
        setButtonEnable()
        playAnimation()

        binding.let {
            editTextListener(it?.edtEmail, "email")
            editTextListener(it?.edtPassword, "password")
        }

        binding?.tvRegister?.setOnClickListener {
            val intent = RegisterActivity.registerActivityIntent(this)
            startActivity(intent)
            finish()
        }

        binding?.btnLogin?.setOnClickListener {
            val email = binding?.edtEmail?.text.toString()
            val password = binding?.edtPassword?.text.toString()

            Toast.makeText(this@LoginActivity, "$email : $password", Toast.LENGTH_SHORT).show()
            goToMainActivity()

//            loginViewModel.login(email, password)
//            showLoading(true)
        }
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
        binding?.btnLogin?.let {
            it.isEnabled = isValidEmail == true && isValidPassword == true
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
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do Nothing
            }
        })
    }

    private fun playAnimation() {
        val logoApp =ObjectAnimator.ofFloat(binding?.tvLogin, View.ALPHA, ALPHA).setDuration(DURATION)
        val welcome = ObjectAnimator.ofFloat(binding?.tvWelcome, View.ALPHA, ALPHA).setDuration(DURATION)
        val tvEmail = ObjectAnimator.ofFloat(binding?.tvEmail, View.ALPHA, ALPHA).setDuration(DURATION)
        val edtEmail = ObjectAnimator.ofFloat(binding?.edtEmail, View.ALPHA, ALPHA).setDuration(DURATION)
        val tvPassword = ObjectAnimator.ofFloat(binding?.tvPassword, View.ALPHA, ALPHA).setDuration(DURATION)
        val edtPassword = ObjectAnimator.ofFloat(binding?.edtPassword, View.ALPHA, ALPHA).setDuration(DURATION)
        val loginBtn = ObjectAnimator.ofFloat(binding?.btnLogin, View.ALPHA, ALPHA).setDuration(DURATION)
        val tvRegister = ObjectAnimator.ofFloat(binding?.tvRegister, View.ALPHA, ALPHA).setDuration(DURATION)

        AnimatorSet().apply {
            playSequentially(
                logoApp,
                welcome,
                tvEmail,
                edtEmail,
                tvPassword,
                edtPassword,
                loginBtn,
                tvRegister
            )
            start()
        }
    }

    private fun goToMainActivity() {
        val intent = MainActivity.mainActivityIntent(this)
        startActivity(intent)
        finish()
    }

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding?.progressBar?.visibility = View.VISIBLE
//        } else {
//            binding?.progressBar?.visibility = View.GONE
//        }
//    }

//    private fun setLoginStatus(error: Boolean) {
//        if (!error) {
//            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
//        }
//    }

    companion object {
        private const val DURATION = 200L
        private const val ALPHA = 1f
        fun loginActivityIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}