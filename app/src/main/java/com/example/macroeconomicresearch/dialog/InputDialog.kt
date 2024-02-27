package com.example.macroeconomicresearch.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.macroeconomicresearch.databinding.EnterTextDialogBinding
import com.example.macroeconomicresearch.db.Database
import com.example.macroeconomicresearch.db.Note

class InputDialog : BaseDialog() {

    companion object {
        fun create(): InputDialog {
            val dialog = InputDialog()
            return dialog
        }

        fun createAndShow(activity: FragmentActivity, name: String? = null)
        {
            create().apply {
                show(activity.supportFragmentManager, "INPUT_DIALOG_TAG")
            }
        }
    }

    private lateinit var binding : EnterTextDialogBinding
    private val positiveBtn
        get() = binding.buttons.positiveButton

    private val negativeBtn
        get() = binding.buttons.negativeButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = EnterTextDialogBinding.inflate(LayoutInflater.from(context))

        positiveBtn.setOnClickListener {
            if(binding.etTitle.text.isNullOrEmpty())
            {
                Toast.makeText(context, "Note cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Database.getInstance(requireContext()).NoteDao().addNote(
                    Note(note = binding.etTitle.text.toString())
                )
                dismiss()
            }
        }
        negativeBtn.setOnClickListener {
            dismiss()
        }

        return createBaseBuilder()
            .setView(binding.root).create()
    }



}