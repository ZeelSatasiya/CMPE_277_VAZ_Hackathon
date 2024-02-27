package com.example.macroeconomicresearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.macroeconomicresearch.LoginType
import com.example.macroeconomicresearch.PreferenceStorage
import com.example.macroeconomicresearch.R
import com.example.macroeconomicresearch.chat.ChatActivity
import com.example.macroeconomicresearch.databinding.LoginFragmentBinding

class LoginFragment : BaseDataBindingFragment<LoginFragmentBinding>(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGo.setOnClickListener {
            PreferenceStorage.loginType = LoginType.GOVT_OFFICIAL
            findNavController().navigate(LoginFragmentDirections.actionLoginToMainScreen())
        }
        binding.btnMr.setOnClickListener {
            PreferenceStorage.loginType = LoginType.RESEARCHER
            findNavController().navigate(LoginFragmentDirections.actionLoginToMainScreen())
        }

        binding.btnChat.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    ChatActivity::class.java
                )
            )
        }

        binding.title.text = "Welcome to Macroeconomic Food security App"
    }
}