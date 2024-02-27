package com.example.macroeconomicresearch

import android.app.Application
import com.example.macroeconomicresearch.retrofit.domain.EnumKey
import com.example.macroeconomicresearch.retrofit.domain.MacroLoadType
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {


    var loadType : EnumKey = MacroLoadType[0]

    override fun onCreate() {
        super.onCreate()
        instance = this

    }



    companion object {
        private var instance: App? = null
        fun getContext(): App {
            return instance!!
        }

    }
}