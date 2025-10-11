package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns

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
                    val email = s?.toString()?.trim().orEmpty()
                    if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        error = "Format email tidak valid"
                    } else {
                        error = null
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}
