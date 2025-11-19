package com.puspa.puspamobile.ui.auth.forgotpassword

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
import com.puspa.puspamobile.data.remote.response.ForgotPasswordRequest
import com.puspa.puspamobile.databinding.ActivityForgotPasswordBinding
import com.puspa.puspamobile.ui.auth.GmailActivity

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ForgotPasswordViewModel::class.java]

        setupImeOption()
        setupAction()
        setupObserver()
    }

    private fun setupImeOption() {
        binding.emailInputLayout.editText?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                binding.btnForgotPassword.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setupAction() {
        binding.imgbtnBack.setOnClickListener {
            finish()
        }
        binding.btnForgotPassword.setOnClickListener {
            val email = binding.emailInputLayout.editText?.text.toString()

            val isEmailValid = binding.emailInputLayout.isValid()

            if (isEmailValid) {
                viewModel.forgotPassword(ForgotPasswordRequest(email))
            }
        }
    }

    private fun setupObserver() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.btnForgotPassword.isEnabled = !isLoading
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.forgotPasswordResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Email Terkirim!!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@ForgotPasswordActivity, GmailActivity::class.java))
            }
            result.onFailure { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}