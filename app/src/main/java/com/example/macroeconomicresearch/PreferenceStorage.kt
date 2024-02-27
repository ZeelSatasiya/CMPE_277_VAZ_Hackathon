package com.example.macroeconomicresearch

import android.app.Activity
import android.preference.PreferenceManager
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

object PreferenceStorage {
    private val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.getContext())
    }

    var loginType : LoginType?
    set(value) {
        prefs.edit {
            putString(LOGIN_TYPE_PREF, value!!.key)
        }
    }
    get() = loginTypeFromStorageKey(prefs.getString(LOGIN_TYPE_PREF, null))



    private const val LOGIN_TYPE_PREF = "login_type"
}




fun <T : ViewDataBinding> Activity.inflate(layoutId : Int) : T
{
    return DataBindingUtil.setContentView(this, layoutId)
}