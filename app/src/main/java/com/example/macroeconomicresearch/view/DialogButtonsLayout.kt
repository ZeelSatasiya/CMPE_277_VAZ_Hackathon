package com.example.macroeconomicresearch.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.res.use
import androidx.core.graphics.ColorUtils
import com.example.macroeconomicresearch.R
import com.example.macroeconomicresearch.Utils
import com.example.macroeconomicresearch.databinding.LayoutDialogButtonsBinding
import com.google.android.material.button.MaterialButton

class DialogButtonsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    @AttrRes defStyleAttr: Int =0,
    @StyleRes defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes)
{

    private val NEGATIVE_BTN_STROKE_STATES = arrayOf(
        intArrayOf(android.R.attr.state_pressed),
        intArrayOf(),
    )



    val binding  = LayoutDialogButtonsBinding.inflate(
        LayoutInflater.from(context), this, true)

    val positiveButton : MaterialButton
        get() = binding.positiveButton

    val negativeButton : MaterialButton
        get() = binding.negativeButton


    init {
        context.obtainStyledAttributes(attrs, R.styleable.DialogButtonsLayout).use {
            binding.positiveButton.text = it.getString(R.styleable.DialogButtonsLayout_android_positiveButtonText)
            binding.negativeButton.text = it.getString(R.styleable.DialogButtonsLayout_android_negativeButtonText)
        }

        val strokeColorsList = IntArray(NEGATIVE_BTN_STROKE_STATES.size)

        val accent = Utils.resolveColor(context, com.google.android.material.R.attr.colorPrimary)
        strokeColorsList[0] = accent
        strokeColorsList[1] = Utils.resolveColor(context, com.google.android.material.R.attr.colorOutline)
        negativeButton.strokeColor = ColorStateList(NEGATIVE_BTN_STROKE_STATES, strokeColorsList)
        negativeButton.setTextColor(accent)
        negativeButton.rippleColor = ColorStateList.valueOf(ColorUtils.setAlphaComponent(accent, 30))
        positiveButton.backgroundTintList = ColorStateList.valueOf(accent)
    }
}