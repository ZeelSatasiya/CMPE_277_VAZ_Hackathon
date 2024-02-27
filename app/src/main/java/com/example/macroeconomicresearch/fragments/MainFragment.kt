package com.example.macroeconomicresearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.macroeconomicresearch.LoginType
import com.example.macroeconomicresearch.PreferenceStorage
import com.example.macroeconomicresearch.R
import com.example.macroeconomicresearch.databinding.FragmentMainBinding
import com.example.macroeconomicresearch.notes.NotesActivity

class MainFragment : BaseDataBindingFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

//        binding.btnNote.isVisible = PreferenceStorage.loginType == LoginType.RESEARCHER
//        binding.btnNote.setOnClickListener {
//            startActivity(Intent(requireActivity(), NotesActivity::class.java))
//        }
    }
}