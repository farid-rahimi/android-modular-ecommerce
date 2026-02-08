package com.solutionium.domain.review

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ReviewDomainModule {

    @Binds
    abstract fun bindGetReviewListPagingUseCase(
        impl: GetReviewListPagingUseCaseImpl
    ): GetReviewListPagingUseCase

    @Binds
    abstract fun bindGetTopReviewsUseCase(
        impl: GetTopReviewsUseCaseImpl
    ): GetTopReviewsUseCase

    @Binds
    abstract fun bindSubmitReviewUseCase(
        impl: SubmitReviewUseCaseImpl
    ): SubmitReviewUseCase

}