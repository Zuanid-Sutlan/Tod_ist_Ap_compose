package com.devstudio.todistap

import android.app.Application
import android.content.Context
import com.devstudio.todistap.data.database.DatabaseGraph
import com.google.android.gms.ads.MobileAds

class ApplicationClass : Application() {

    companion object{
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        MobileAds.initialize(this) {}

        DatabaseGraph.databaseProvider(this)


    }

}