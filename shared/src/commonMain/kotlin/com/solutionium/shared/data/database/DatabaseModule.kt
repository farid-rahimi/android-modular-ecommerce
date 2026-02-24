package com.solutionium.shared.data.database

import org.koin.core.module.Module
import org.koin.dsl.module

internal val databaseDaoModule = module {
    single { get<WooDatabase>().productDetailDao() }
    single { get<WooDatabase>().cartDao() }
    single { get<WooDatabase>().addressDao() }
    single { get<WooDatabase>().favoriteDao() }
    single { get<WooDatabase>().storyViewDao() }
}

expect val databaseModule: Module
