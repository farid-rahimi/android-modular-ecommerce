package com.solutionium.data.database

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), WooDatabase::class.java, "woo-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<WooDatabase>().productDetailDao() }
    single { get<WooDatabase>().cartDao() }
    single { get<WooDatabase>().addressDao() }
    single { get<WooDatabase>().favoriteDao() }
    single { get<WooDatabase>().storyViewDao() }
}
