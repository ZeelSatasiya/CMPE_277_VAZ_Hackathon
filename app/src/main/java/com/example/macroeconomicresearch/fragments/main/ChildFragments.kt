package com.example.macroeconomicresearch.fragments.main


import com.example.macroeconomicresearch.retrofit.domain.AgricultureLoadType
import com.example.macroeconomicresearch.retrofit.domain.DebtLoadType
import com.example.macroeconomicresearch.retrofit.domain.MacroLoadType

class AgriculturalFragment : BaseFragment(){
    override fun getLoadType() = AgricultureLoadType
}

class MacroeconomicFragment : BaseFragment(){
    override fun getLoadType() = MacroLoadType
}

class DebtFragment : BaseFragment(){
    override fun getLoadType() = DebtLoadType
}
