package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet

class NameInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr) {

    init {
        hint = "enter username"
        post {
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val name = s?.toString()?.trim().orEmpty()
                    if (name.isEmpty()) {
                        error = "Nama tidak boleh kosong"
                    } else {
                        error = null
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}
