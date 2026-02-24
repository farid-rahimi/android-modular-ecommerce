package com.solutionium.shared.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val androidLocalModule = module {
    single<Settings>(named("EncPrefs")) {
        val context = androidContext()
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPrefs = EncryptedSharedPreferences.create(
            context,
            "auth_woo_qein_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        // Wrap SharedPreferences in KMP-compatible Settings object
        SharedPreferencesSettings(sharedPrefs)
    }

    single<Settings>(named("AppPrefs")) {
        SharedPreferencesSettings(
            androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        )
    }
}