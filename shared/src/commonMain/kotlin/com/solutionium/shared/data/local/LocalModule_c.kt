//package com.solutionium.shared.data.local
//
//import org.koin.android.ext.koin.androidContext
//import org.koin.dsl.module
//
//val localModule = module {
//
//    single<TokenStore> {
//        AuthTokenLocalDataSource(androidContext())
//    }
//
//    single<AppPreferences> {
//        AppPreferencesImpl(androidContext())
//    }
//
//}
