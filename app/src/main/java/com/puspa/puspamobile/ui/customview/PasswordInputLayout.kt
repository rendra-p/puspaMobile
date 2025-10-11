package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import com.puspa.puspamobile.R

class PasswordInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr) {

    private var isPasswordVisible = false

    init {
        hint = "Password"
        endIconMode = END_ICON_CUSTOM
        setEndIconDrawable(R.drawable.ic_visibility_off)
        endIconContentDescription = "Tampilkan Password"

        setEndIconTintList(null)

        editText?.transformationMethod = PasswordTransformationMethod.getInstance()

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
