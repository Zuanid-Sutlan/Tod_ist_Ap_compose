package com.devstudio.todistap.Utils

import android.preference.PreferenceManager
import com.devstudio.todistap.ApplicationClass


object Prefs {


    private val sharedPreference = PreferenceManager.getDefaultSharedPreferences(ApplicationClass.appContext)

    @JvmStatic
    var isDarkTheme : Boolean
        get() = sharedPreference.getBoolean("isDarkTheme", false)
        set(isDarkTheme){
            sharedPreference.edit().putBoolean("isDarkTheme",isDarkTheme).apply()
        }


}