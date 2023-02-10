package com.android.mvvm_composeui_architecture

import android.app.Application
import android.content.Context
import com.android.mvvm_composeui_architecture.network.RequestManager
import dagger.hilt.android.HiltAndroidApp

/**
 * @author kevin
 * @description
 */
@HiltAndroidApp
class SampleApp : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        RequestManager.init(this)
    }

}