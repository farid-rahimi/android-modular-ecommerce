package com.solutionium.domain.config

import com.solutionium.domain.config.impl.ForcedEnabledPaymentUseCaseImpl
import com.solutionium.domain.config.impl.GetAppImagesImpl
import com.solutionium.domain.config.impl.GetBACSDetailsUseCaseImpl
import com.solutionium.domain.config.impl.GetContactInfoUseCaseImpl
import com.solutionium.domain.config.impl.GetHeaderLogoUseCaseImpl
import com.solutionium.domain.config.impl.GetPrivacyPolicyUseCaseImpl
import com.solutionium.domain.config.impl.GetStoriesUseCaseImpl
import com.solutionium.domain.config.impl.GetVersionsUseCaseImpl
import com.solutionium.domain.config.impl.HomeBannersUseCaseImpl
import com.solutionium.domain.config.impl.PaymentMethodDiscountUseCaseImpl
import com.solutionium.domain.config.impl.PriceManagerImpl
import com.solutionium.domain.config.impl.ReviewCriteriaUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConfigDomainModule {

    @Binds
    abstract fun bindPaymentMethodDiscountUseCase(
        impl: PaymentMethodDiscountUseCaseImpl
    ): PaymentMethodDiscountUseCase

    @Binds
    abstract fun bindGetAppImages(
        impl: GetAppImagesImpl
    ): GetAppImages

    @Binds
    abstract fun bindHomeBannersUseCase(
        impl: HomeBannersUseCaseImpl
    ): HomeBannersUseCase

    @Binds
    abstract fun bindGetStoriesUseCase(
        impl: GetStoriesUseCaseImpl
    ): GetStoriesUseCase

    @Binds
    abstract fun bindGetHeaderLogoUseCase(
        impl: GetHeaderLogoUseCaseImpl
    ): GetHeaderLogoUseCase

    @Binds
    abstract fun bindGetBACSDetailsUseCase(
        impl: GetBACSDetailsUseCaseImpl
    ): GetBACSDetailsUseCase

    @Binds
    abstract fun bindReviewCriteriaUseCare(
        impl: ReviewCriteriaUseCaseImpl
    ): ReviewCriteriaUseCase

    @Binds
    abstract fun bindGetConfigUseCase(
        impl: GetPrivacyPolicyUseCaseImpl
    ): GetPrivacyPolicyUseCase

    @Binds
    abstract fun bindForcedEnabledPaymentUseCase(
        impl: ForcedEnabledPaymentUseCaseImpl
    ): ForcedEnabledPaymentUseCase

    @Binds
    abstract fun bindGetVersionsUseCase(
        impl: GetVersionsUseCaseImpl
    ): GetVersionsUseCase

    @Binds
    abstract fun getContactInfoUseCase(
        impl: GetContactInfoUseCaseImpl
    ): GetContactInfoUseCase
}