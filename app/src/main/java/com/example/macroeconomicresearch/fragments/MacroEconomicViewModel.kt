package com.example.macroeconomicresearch.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.macroeconomicresearch.retrofit.domain.EnumKey
import com.example.macroeconomicresearch.retrofit.domain.MacroEconomicUSDLoadUseCase
import com.example.macroeconomicresearch.retrofit.domain.MacroLoadType
import com.example.macroeconomicresearch.retrofit.domain.Result
import com.example.macroeconomicresearch.retrofit.response.Root3

class GraphViewModel : ViewModel() {


     val macroEconomicUSDLoadUseCase = MacroEconomicUSDLoadUseCase()

    fun loadData(loadType: EnumKey, fromDate : String, toDate: String, country : String): LiveData<Result<List<Any>>> {
        macroEconomicUSDLoadUseCase.execute(MacroEconomicUSDLoadUseCase.Input(country, fromDate, toDate, loadType))
        return macroEconomicUSDLoadUseCase.observe()
    }
}