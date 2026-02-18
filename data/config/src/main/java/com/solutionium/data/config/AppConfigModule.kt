package com.solutionium.data.config

import com.solutionium.data.config.impl.AppConfigRepositoryImpl
import org.koin.dsl.module

val appConfigDataModule = module {
    single<AppConfigRepository> { AppConfigRepositoryImpl(get()) }
}
