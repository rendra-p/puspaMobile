package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import com.puspa.puspamobile.R

class PasswordInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr) {

    private var isPasswordVisible = false

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PasswordInputLayout)
        val customHint = a.getString(R.styleable.PasswordInputLayout_android_hint)
        a.recycle()

        hint = customHint ?: "Password"
        endIconMode = END_ICON_CUSTOM
        setEndIconDrawable(R.drawable.ic_visibility_off)
        endIconContentDescription = "Tampilkan Password"

        setEndIconTintList(null)

        setEndIconOnClickListener {
            val editText = editText ?: return@setEndIconOnClickListener
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
                editText.transformationMethod = null
                setEndIconDrawable(R.drawable.ic_visibility)
                endIconContentDescription = "Sembunyikan Password"
            } else {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                setEndIconDrawable(R.drawable.ic_visibility_off)
                endIconContentDescription = "Tampilkan Password"
            }

            editText.setSelection(editText.text?.length ?: 0)
            setEndIconTintList(null)
        }

        post {
            editText?.transformationMethod = PasswordTransformationMethod.getInstance()
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validateInput()
                }
                override fun afterTextChanged(s: Editable?) {}
            })
            editText?.imeOptions = EditorInfo.IME_ACTION_DONE
        }
    }

    fun isValid(): Boolean = validateInput()

    private fun validateInput(): Boolean {
        val password = editText?.text?.toString().orEmpty()

        return when {
            password.isEmpty() -> {
                isErrorEnabled = true
                error = "Tidak boleh kosong"
                false
            }
            password.length < 8 -> {
                isErrorEnabled = true
                error = "Minimal 8 karakter"
                false
            }
            !password.any { it.isUpperCase() } -> {
                isErrorEnabled = true
                error = "Harus ada huruf besar"
                false
            }
            !password.any { it.isDigit() } -> {
                isErrorEnabled = true
                error = "Harus ada angka"
                false
            }
            !password.any { !it.isLetterOrDigit() } -> {
                isErrorEnabled = true
                error = "Harus ada simbol"
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
