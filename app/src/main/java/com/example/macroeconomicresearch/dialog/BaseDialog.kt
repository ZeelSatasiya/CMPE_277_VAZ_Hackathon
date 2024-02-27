package com.example.macroeconomicresearch.dialog


import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.macroeconomicresearch.R
import com.example.macroeconomicresearch.Utils.dpToPx
import com.example.macroeconomicresearch.Utils.resolveColor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.elevation.ElevationOverlayProvider

abstract class BaseDialog  : DialogFragment()
{


    fun createBaseBuilder() : MaterialAlertDialogBuilder
    {
        val dialogBackground = ContextCompat.getDrawable(requireContext(), R.drawable.dialog_background)

        val surfaceColor = resolveColor(requireContext(), com.google.android.material.R.attr.colorSurface, Color.BLACK)
        val elevation =4f.dpToPx(requireContext())
        dialogBackground?.setTint(ElevationOverlayProvider(requireContext()).compositeOverlay(surfaceColor, elevation))
        return MaterialAlertDialogBuilder(requireContext(), R.style.OtoDialog).setBackground(dialogBackground)
    }


}