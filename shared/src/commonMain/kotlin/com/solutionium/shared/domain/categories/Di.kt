package com.solutionium.shared.domain.categories

import com.solutionium.shared.data.categories.categoryDataModule
import org.koin.dsl.module

fun getCategoryDomainModules() = setOf(categoryDomainModule, categoryDataModule)


val categoryDomainModule = module {
    factory<GetCategoryListUseCase> { GetCategoryListUseCaseImpl(get()) }
}
