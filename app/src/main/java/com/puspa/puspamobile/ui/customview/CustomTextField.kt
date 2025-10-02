package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.puspa.puspamobile.R
import java.util.regex.Pattern

class CustomTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private val etInput: TextInputEditText
    private val btnTogglePassword: ImageButton
    private var inputType: InputType = InputType.USERNAME_EMAIL
    private var isPasswordVisible = false

    enum class InputType {
        USERNAME_EMAIL, // Fleksibel untuk username atau email
        USERNAME,
        EMAIL,
        PASSWORD
    }

    init {
        // Inflate layout
        val view = LayoutInflater.from(context).inflate(R.layout.custom_textfield, this)
        etInput = view.findViewById(R.id.etInput)
        btnTogglePassword = view.findViewById(R.id.btnTogglePassword)

        // Setup toggle password
        btnTogglePassword.setOnClickListener {
            togglePasswordVisibility()
        }

        // Parse attributes dari XML
        parseAttributes(attrs)

        // Setup validasi
        setupValidation()

        // Set initial input type
        updateInputType()
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextField)
        try {
            val typeOrdinal = typedArray.getInt(R.styleable.CustomTextField_inputType, 0)
            inputType = InputType.entries.toTypedArray()[typeOrdinal]
            hint = typedArray.getString(R.styleable.CustomTextField_hint) ?: "Enter text"
        } finally {
            typedArray.recycle()
        }
    }

    private fun setupValidation() {
        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validateInput(s.toString())
            }
        })
    }

    private fun updateInputType() {
        when (inputType) {
            InputType.USERNAME_EMAIL, InputType.USERNAME -> {
                etInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                btnTogglePassword.visibility = View.GONE
            }
            InputType.EMAIL -> {
                etInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                btnTogglePassword.visibility = View.GONE
            }
            InputType.PASSWORD -> {
                etInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnTogglePassword.visibility = View.VISIBLE
            }
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            etInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            btnTogglePassword.setImageResource(R.drawable.ic_visibility_off) // Ganti dengan icon off
        } else {
            etInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            btnTogglePassword.setImageResource(R.drawable.ic_visibility)
        }
        etInput.setSelection(etInput.text?.length ?: 0) // Pindah cursor ke akhir
    }

    private fun validateInput(text: String) {
        error = when (inputType) {
            InputType.USERNAME_EMAIL -> {
                if (text.isEmpty()) R.string.error_required
                else if (isValidEmail(text) || text.length >= 3) null // Fleksibel: email atau username minimal 3 char
                else R.string.error_username_min_length
            }
            InputType.USERNAME -> {
                if (text.isEmpty()) R.string.error_required
                else if (text.length < 3) R.string.error_username_min_length
                else null
            }
            InputType.EMAIL -> {
                if (text.isEmpty()) R.string.error_required
                else if (!isValidEmail(text)) R.string.error_email_invalid
                else null
            }
            InputType.PASSWORD -> {
                if (text.isEmpty()) R.string.error_required
                else if (text.length < 8) R.string.error_password_min_length
                else if (!hasUppercase(text)) R.string.error_password_uppercase
                else if (!hasDigit(text)) R.string.error_password_digit
                else if (!hasSymbol(text)) R.string.error_password_symbol
                else null
            }
        }?.let { context.getString(it) }
    }

    // Helper functions untuk validasi
    private fun isValidEmail(email: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+",
            Pattern.CASE_INSENSITIVE
        )
        return pattern.matcher(email).matches()
    }

    private fun hasUppercase(text: String): Boolean {
        return text.any { it.isUpperCase() }
    }

    private fun hasDigit(text: String): Boolean {
        return text.any { it.isDigit() }
    }

    private fun hasSymbol(text: String): Boolean {
        val symbolPattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+")
        return symbolPattern.matcher(text).find()
    }

    // Public methods untuk akses dari luar
    fun getText(): String = etInput.text.toString().trim()
    fun setInputType(type: InputType) {
        this.inputType = type
        updateInputType()
        validateInput(getText())
    }
    fun isValid(): Boolean = error == null && getText().isNotEmpty()
}
