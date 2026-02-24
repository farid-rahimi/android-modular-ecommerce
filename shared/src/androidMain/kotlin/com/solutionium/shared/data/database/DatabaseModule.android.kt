package com.solutionium.shared.data.database

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module = module {
    includes(databaseDaoModule)

    single<WooDatabase> {
        val appContext = androidContext()
        val dbFile = appContext.getDatabasePath("woo_database")
        Room.databaseBuilder<WooDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
            .setQueryCoroutineContext(Dispatchers.IO)
            .setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
            .build()
    }
}
