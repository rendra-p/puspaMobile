package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.inputmethod.EditorInfo

class NameEmailInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr) {

    init {
        boxBackgroundMode = BOX_BACKGROUND_OUTLINE
        hint = "Nama atau Email"
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

    fun isValid(): Boolean {
        return validateInput()
    }

    private fun validateInput(): Boolean {
        val input = editText?.text?.toString()?.trim().orEmpty()
        val isPotentiallyEmail = input.contains("@")
        val isEmail = Patterns.EMAIL_ADDRESS.matcher(input).matches()

        return when {
            input.isEmpty() -> {
                isErrorEnabled = true
                error = "Field tidak boleh kosong"
                false
            }
            !isEmail && input.length < 3 -> {
                isErrorEnabled = true
                error = "Input minimal 3 karakter"
                false
            }
            isPotentiallyEmail && !isEmail -> {
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
