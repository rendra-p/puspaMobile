package com.puspa.puspamobile.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.puspa.puspamobile.MainActivity
import com.puspa.puspamobile.R
import com.puspa.puspamobile.ViewModelFactory
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        setupImeOptions()
        setupAction()
        setupObserver()
    }

    private fun setupImeOptions() {
        binding.nameEmailInputLayout.editText?.nextFocusForwardId = binding.passwordInputLayout.id

        binding.passwordInputLayout.editText?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                binding.btnMasukLogin.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setupAction() {
        binding.imgbtnBack.setOnClickListener {
            finish()
        }
        binding.toResetPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))
        }
        binding.toDaftar.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
        binding.btnMasukLogin.setOnClickListener {
            val identifier = binding.nameEmailInputLayout.editText?.text.toString()
            val password = binding.passwordInputLayout.editText?.text.toString()

            if (identifier.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(identifier, password)
                viewModel.login(loginRequest)
            } else {
                Toast.makeText(this, "Email/Username dan Password tidak boleh kosong.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObserver() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.loginResult.observe(this) { result ->
            result.onSuccess { response ->
                if (response.success == true) {
                    Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                } else {
                    Toast.makeText(this, "Login gagal: ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
            result.onFailure { exception ->
                Toast.makeText(this, "Terjadi kesalahan: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.btnMasukLogin.isEnabled = !isLoading
    }
}