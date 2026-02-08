package com.solutionium.woo

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import android.content.res.Configuration


@HiltAndroidApp
class WooApplication : Application() {

//    override fun attachBaseContext(base: Context) {
//        // This is the most reliable place to force the app's locale.
//        // It runs before any activity or other component is created.
//        val localeToSet = Locale("fa")
//        Locale.setDefault(localeToSet)
//
//        val config = Configuration(base.resources.configuration)
//        config.setLocale(localeToSet)
//        config.setLayoutDirection(localeToSet)
//
//        val updatedContext = base.createConfigurationContext(config)
//        super.attachBaseContext(updatedContext)
//    }
}
