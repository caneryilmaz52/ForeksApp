package com.caneryilmazapps.foreksapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.paperdb.Paper

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }
}