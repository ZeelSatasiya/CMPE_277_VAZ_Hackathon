package com.example.macroeconomicresearch.retrofit.domain

import android.util.Log
import com.example.macroeconomicresearch.retrofit.MacroeconomicService
import com.example.macroeconomicresearch.retrofit.response.Root3

class MacroEconomicUSDLoadUseCase : MediatorUseCase<MacroEconomicUSDLoadUseCase.Input, List<Any>>() {

    data class Input(val country : String, val fromDate : String, val toDate : String, val loadType: EnumKey)



    override fun execute(parameters: Input) {
        result.value = Result.Loading
        MacroeconomicService.service.getMacroeconomic(parameters.loadType.storageKey, parameters.country, parameters.fromDate+":"+parameters.toDate).enqueue {
//                Log.e("MacroEconomicUSDLoadUseCase","${it}")

                when(it)
                {
                    is Result.Success ->
                    {
                        val response = it.data
                        if (response.isSuccessful && response.body() != null) {
                            Log.e("MacroEconomicUSDLoadUseCase","${response.body()!!}")
                            result.postValue(Result.Success(response.body()!![1] as List<Any>))
                        }
                        else {
                            result.postValue(Result.Error(Exception(response.message())))
                        }
                    }
                    is Result.Error ->
                    {
                        Log.e("ERRORRR", "$it")
                        it.exception.printStackTrace()
                        result.postValue(Result.Error(it.exception))
                    }
                    else ->{}

                }
            }

    }
}