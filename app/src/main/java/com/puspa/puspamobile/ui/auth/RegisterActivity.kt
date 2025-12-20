package com.puspa.puspamobile.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.RegisterRequest
import com.puspa.puspamobile.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        setupImeOptions()
        setupAction()
        setupObserver()
    }

    private fun setupImeOptions() {
        binding.nameInputLayout.editText?.nextFocusForwardId = binding.emailInputLayout.id
        binding.emailInputLayout.editText?.nextFocusForwardId = binding.passwordInputLayout.id

        binding.passwordInputLayout.editText?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                binding.btnDaftarRegister.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
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
            val username = binding.nameInputLayout.editText?.text.toString()
            val email = binding.emailInputLayout.editText?.text.toString()
            val password = binding.passwordInputLayout.editText?.text.toString()

            val isUsernameValid = binding.nameInputLayout.isValid()
            val isEmailValid = binding.emailInputLayout.isValid()
            val isPasswordValid = binding.passwordInputLayout.isValid()

            if (isUsernameValid && isEmailValid && isPasswordValid) {
                val registerRequest = RegisterRequest(username, email, password)
                viewModel.register(registerRequest)
            }
        }
    }

    private fun setupObserver() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.btnDaftarRegister.isEnabled = !isLoading
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.registerResult.observe(this) { result ->
            result.onSuccess { response ->
                if (response.success == true) {
                    Toast.makeText(
                        this,
                        response.message ?: "Pendaftaran berhasil!",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(Intent(this, GmailActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        response.message ?: "Registrasi gagal. Coba lagi nanti.",
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
}