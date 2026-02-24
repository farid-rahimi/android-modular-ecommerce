package com.solutionium.shared.data.database

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val databaseModule: Module = module {
    includes(databaseDaoModule)

    single<WooDatabase> {
        val dbFilePath = NSHomeDirectory() + "/woo_database"
        Room.databaseBuilder<WooDatabase>(
            name = dbFilePath,
            factory = { WooDatabaseConstructor.initialize() }
        )
            .setQueryCoroutineContext(Dispatchers.Default)
            .setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
            .build()
    }
}
