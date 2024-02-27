package com.example.macroeconomicresearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.macroeconomicresearch.databinding.ActivityMainBinding
import com.example.macroeconomicresearch.fragments.GraphViewModel
import com.example.macroeconomicresearch.retrofit.domain.Result

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment : NavHostFragment

    val mainNavController: NavController
        get() {
            return navHostFragment.navController
        }

    private lateinit var binding : ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment



    }
}