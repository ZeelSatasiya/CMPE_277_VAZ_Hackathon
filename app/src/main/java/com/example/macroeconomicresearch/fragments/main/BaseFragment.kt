package com.example.macroeconomicresearch.fragments.main

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.macroeconomicresearch.App
import com.example.macroeconomicresearch.GraphActivity
import com.example.macroeconomicresearch.R
import com.example.macroeconomicresearch.Utils.dpToPx
import com.example.macroeconomicresearch.databinding.MainFragmentHostBinding
import com.example.macroeconomicresearch.fragments.BaseDataBindingFragment
import com.example.macroeconomicresearch.retrofit.domain.EnumKey
import com.google.android.material.transition.MaterialFadeThrough
import kotlin.math.roundToInt


abstract class BaseFragment : BaseDataBindingFragment<MainFragmentHostBinding>(R.layout.fragment_main_child) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val padding = 16f.dpToPx(requireContext()).roundToInt()
        binding.radioGroup.apply {
            getLoadType().forEachIndexed { index, enumKey ->
                val rb  = RadioButton(requireContext())
                val params = RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                rb.layoutParams = params
                rb.setPadding(padding, padding, padding, padding)
                rb.text = enumKey.label
                rb.id = index
                addView(rb)
            }
        }

        binding.apply.setOnClickListener {
            (requireActivity().application as App).loadType = getLoadType()[binding.radioGroup.checkedRadioButtonId]
            startActivity(Intent(requireActivity(), GraphActivity::class.java))
        }

    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, R.anim.open_enter)
        } else {
            AnimationUtils.loadAnimation(context, R.anim.open_exit)
        }
    }

    abstract fun getLoadType() : List<EnumKey>
}