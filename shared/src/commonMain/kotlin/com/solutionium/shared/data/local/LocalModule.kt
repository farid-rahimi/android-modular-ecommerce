package com.solutionium.shared.data.local

import org.koin.core.qualifier.named
import org.koin.dsl.module

val localModule = module {
    single<TokenStore> { AuthTokenLocalDataSource(get(named("EncPrefs"))) }

    single<AppPreferences> {
        AppPreferencesImpl(get(named("AppPrefs")))
    }
}
