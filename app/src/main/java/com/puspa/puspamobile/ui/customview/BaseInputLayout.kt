package com.puspa.puspamobile.ui.customview

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import com.puspa.puspamobile.R

open class BaseInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    init {
        setBoxBackgroundMode(BOX_BACKGROUND_OUTLINE)
        setBoxCornerRadii(16F,16F,16F,16F)
        setHintTextColor(resources.getColorStateList(R.color.onGradientVariant, context.theme))
        setBoxStrokeColorStateList(resources.getColorStateList(R.color.onGradientVariant, context.theme))
        setHintTextAppearance(R.style.Playpen_Label)
    }
}
