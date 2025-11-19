package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.inputmethod.EditorInfo

class EmailInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr) {

    init {
        hint = "Email"
        post {
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validateInput()
                }
                override fun afterTextChanged(s: Editable?) {}
            })
            editText?.imeOptions = EditorInfo.IME_ACTION_NEXT
        }
    }

    fun isValid(): Boolean = validateInput()

    private fun validateInput(): Boolean {
        val email = editText?.text?.toString().orEmpty()
        val isEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        return when {
            email.isEmpty() -> {
                isErrorEnabled = true
                error = "Email tidak boleh kosong"
                false
            }
            !isEmail -> {
                isErrorEnabled = true
                error = "Format email tidak valid"
                false
            }
            else -> {
                isErrorEnabled = false
                error = null
                true
            }
        }
    }
}
