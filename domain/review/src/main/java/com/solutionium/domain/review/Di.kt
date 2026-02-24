package com.solutionium.domain.review

import com.solutionium.shared.data.products.productsDataModule
import org.koin.dsl.module

fun getReviewDomainModules() = listOf(reviewDomainModule, productsDataModule)


val reviewDomainModule = module {
    factory<GetReviewListPagingUseCase> { GetReviewListPagingUseCaseImpl(get()) }
    factory<GetTopReviewsUseCase> { GetTopReviewsUseCaseImpl(get()) }
    factory<SubmitReviewUseCase> { SubmitReviewUseCaseImpl(get()) }
}
