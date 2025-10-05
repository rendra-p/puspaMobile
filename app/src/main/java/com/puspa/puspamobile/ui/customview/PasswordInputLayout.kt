package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet

class PasswordInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr) {

    init {
        hint = "Password"
        isEndIconVisible = true
        endIconMode = END_ICON_PASSWORD_TOGGLE

        post {
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val password = s?.toString().orEmpty()
                    when {
                        password.isEmpty() -> error = "Password tidak boleh kosong"
                        password.length < 8 -> error = "Minimal 8 karakter"
                        !password.any { it.isUpperCase() } -> error = "Harus ada huruf besar"
                        !password.any { it.isDigit() } -> error = "Harus ada angka"
                        !password.any { !it.isLetterOrDigit() } -> error = "Harus ada simbol"
                        else -> error = null
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}
