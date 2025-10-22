package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo

class NameInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr) {

    init {
        hint = "Nama"
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
        val name = editText?.text?.toString().orEmpty()
        return when {
            name.isEmpty() -> {
                isErrorEnabled = true
                error = "Nama tidak boleh kosong"
                false
            }
            name.length < 3 -> {
                isErrorEnabled = true
                error = "Nama minimal 3 karakter"
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
