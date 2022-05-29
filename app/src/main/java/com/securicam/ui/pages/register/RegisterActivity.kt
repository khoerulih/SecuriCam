package com.securicam.ui.pages.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.securicam.R
import com.securicam.databinding.ActivityRegisterBinding
import com.securicam.ui.pages.login.LoginActivity
import com.securicam.utils.goToLoginActivity

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding

    private var isValidUsername: Boolean? = null
    private var isValidEmail: Boolean? = null
    private var isValidPassword: Boolean? = null
    private lateinit var role: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupView()
        setButtonEnable()
        playAnimation()

        val items = resources.getStringArray(R.array.roles)
        val adapter = ArrayAdapter(
            this,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            items
        )
        binding?.spinnerRole?.adapter = adapter

        binding?.spinnerRole?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                role = items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


        val registerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[RegisterViewModel::class.java]

        registerViewModel.isError.observe(this) { error ->
            showLoading(false)
            if (error) {
                Toast.makeText(this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT)
                    .show()

                goToLoginActivity(this)
                finish()
            }
        }

        binding.let {
            editTextListener(it?.edtUsername, "username")
            editTextListener(it?.edtEmail, "email")
            editTextListener(it?.edtPassword, "password")
        }

        binding?.tvLogin?.setOnClickListener {
            goToLoginActivity(this)
            finish()
        }

        binding?.btnRegister?.setOnClickListener {
            val name = binding?.edtUsername?.text.toString()
            val email = binding?.edtEmail?.text.toString()
            val password = binding?.edtPassword?.text.toString()

            registerViewModel.register(name, email, password, role)
            showLoading(true)
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun playAnimation() {
        val logoApp = ObjectAnimator.ofFloat(binding?.tvRegister, View.ALPHA, ALPHA).setDuration(
            DURATION
        )
        val tvUsername = ObjectAnimator.ofFloat(binding?.tvName, View.ALPHA, ALPHA).setDuration(
            DURATION
        )
        val edtUsername =
            ObjectAnimator.ofFloat(binding?.edtUsername, View.ALPHA, ALPHA).setDuration(
                DURATION
            )
        val tvEmail = ObjectAnimator.ofFloat(binding?.tvEmail, View.ALPHA, ALPHA).setDuration(
            DURATION
        )
        val edtEmail = ObjectAnimator.ofFloat(binding?.edtEmail, View.ALPHA, ALPHA).setDuration(
            DURATION
        )
        val tvPassword = ObjectAnimator.ofFloat(
            binding?.tvPassword, View.ALPHA,
            ALPHA
        ).setDuration(DURATION)
        val edtPassword = ObjectAnimator.ofFloat(
            binding?.edtPassword, View.ALPHA,
            ALPHA
        ).setDuration(DURATION)
        val registerBtn =
            ObjectAnimator.ofFloat(binding?.btnRegister, View.ALPHA, ALPHA).setDuration(
                DURATION
            )
        val tvLogin = ObjectAnimator.ofFloat(
            binding?.tvLogin, View.ALPHA,
            ALPHA
        ).setDuration(DURATION)
        val tvRole = ObjectAnimator.ofFloat(binding?.tvRole, View.ALPHA, ALPHA).setDuration(DURATION)
        val spinnerRole = ObjectAnimator.ofFloat(binding?.spinnerRole, View.ALPHA, ALPHA).setDuration(DURATION)

        AnimatorSet().apply {
            playSequentially(
                logoApp,
                tvUsername,
                edtUsername,
                tvEmail,
                edtEmail,
                tvPassword,
                edtPassword,
                registerBtn,
                tvLogin,
                tvRole,
                spinnerRole
            )
            start()
        }
    }

    companion object {
        private const val DURATION = 200L
        private const val ALPHA = 1f
        fun registerActivityIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }
}