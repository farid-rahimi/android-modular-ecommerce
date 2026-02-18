package com.solutionium.domain.woo.categories

import com.solutionium.data.woo.categories.categoryDataModule
import org.koin.dsl.module

fun getCategoryDomainModules() = setOf(categoryDomainModule, categoryDataModule)


val categoryDomainModule = module {
    factory<GetCategoryListUseCase> { GetCategoryListUseCaseImpl(get()) }
}
