package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns

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
                    val input = s?.toString()?.trim().orEmpty()
                    if (input.isEmpty()) {
                        error = "Field tidak boleh kosong"
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches() && input.contains("@")) {
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
