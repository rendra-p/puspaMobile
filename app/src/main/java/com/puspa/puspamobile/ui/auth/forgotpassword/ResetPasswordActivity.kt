package com.puspa.puspamobile.ui.auth.forgotpassword

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.ResetPasswordRequest
import com.puspa.puspamobile.databinding.ActivityResetPasswordBinding
import com.puspa.puspamobile.ui.auth.BoardingActivity

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ResetPasswordViewModel::class.java]

        setupImeOptions()
        setupAction()
        setupObserver()
    }

    private fun setupImeOptions() {
        binding.passwordInputLayout.editText?.nextFocusForwardId = binding.confirmPasswordInputLayout.id

        binding.confirmPasswordInputLayout.editText?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                binding.btnResetPassword.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setupAction() {
        onBackPressedDispatcher.addCallback(this) {
            finishAffinity()
        }
        binding.btnResetPassword.setOnClickListener {
            val password = binding.passwordInputLayout.editText?.text.toString()
            val confirmPassword = binding.confirmPasswordInputLayout.editText?.text.toString()
            binding.passwordInputLayout.error = null
            binding.confirmPasswordInputLayout.error = null

            val isPasswordValid = binding.passwordInputLayout.isValid()
            val isConfirmPasswordValid = binding.confirmPasswordInputLayout.isValid()
            if (!isPasswordValid || !isConfirmPasswordValid) {
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.confirmPasswordInputLayout.error = "Password tidak sama"
                return@setOnClickListener
            }

            val data: Uri? = intent?.data

            if (data != null) {
                val token = data.getQueryParameter("token")
                val email = data.getQueryParameter("email")

                if (token.isNullOrEmpty() || email.isNullOrEmpty()) {
                    Toast.makeText(this, "Invalid reset link", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewModel.resetPassword(token, email, ResetPasswordRequest(password, confirmPassword))
            }
        }
    }

    private fun setupObserver() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.btnResetPassword.isEnabled = !isLoading
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.resetPasswordResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Password Berhasil Diubah!!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, BoardingActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
            result.onFailure { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}