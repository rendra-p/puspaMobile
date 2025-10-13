package com.puspa.puspamobile.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.R
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.LoginRequest
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.databinding.ActivityLoginBinding
import com.puspa.puspamobile.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        setupAction()
        setupObserver()
    }

    private fun setupAction() {
        binding.imgbtnBack.setOnClickListener {
            finish()
        }
        binding.toMasuk.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
        binding.btnDaftarRegister.setOnClickListener {
            val email = binding.emailInputLayout.editText?.text.toString()
            val username = binding.nameInputLayout.editText?.text.toString()
            val password = binding.passwordInputLayout.editText?.text.toString()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                val registerRequest = RegisterRequest(username, email, password)
                viewModel.register(registerRequest)
            } else {
                Toast.makeText(this, "Email, Username, dan Password tidak boleh kosong.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObserver() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.registerResult.observe(this) { result ->
            result.onSuccess { response ->
                if (response.success == true) {
                    Toast.makeText(
                        this,
                        response.message ?: "Pendaftaran berhasil!",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Arahkan ke login setelah pendaftaran sukses
                    startActivity(Intent(this, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                } else {
                    // Tampilkan pesan error validasi dari API
                    val emailError = response.errors?.email?.joinToString("\n")
                    val usernameError = response.errors?.username?.joinToString("\n")

                    binding.emailInputLayout.error = emailError
                    binding.nameInputLayout.error = usernameError

                    Toast.makeText(
                        this,
                        response.message ?: "Registrasi gagal. Periksa input Anda.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            result.onFailure { exception ->
                Toast.makeText(
                    this,
                    "Terjadi kesalahan: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.btnDaftarRegister.isEnabled = !isLoading
        binding.btnDaftarRegister.alpha = if (isLoading) 0.6f else 1f
    }
}