package com.solutionium.data.woo.user

import com.solutionium.data.api.woo.apiModule
import com.solutionium.data.api.woo.getApiModule
import com.solutionium.data.database.databaseModule
import com.solutionium.data.local.localModule
import org.koin.dsl.module

fun getUserDataModules() = setOf(userDataModule, databaseModule, localModule) + getApiModule()

val userDataModule = module {
    single<WooUserRepository> { WooUserRepositoryImpl(get(), get(), get(), get()) }
    single<StoryViewRepository> { StoryViewRepositoryImpl(get()) }
}
