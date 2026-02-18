package com.solutionium.domain.config

import com.solutionium.data.config.appConfigDataModule
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
import com.solutionium.domain.config.impl.ReviewCriteriaUseCaseImpl
import org.koin.dsl.module

fun getConfigDomainModules() = setOf(configDomainModule, appConfigDataModule)


val configDomainModule = module {
    factory<PaymentMethodDiscountUseCase> { PaymentMethodDiscountUseCaseImpl(get()) }
    factory<GetAppImages> { GetAppImagesImpl(get()) }
    factory<HomeBannersUseCase> { HomeBannersUseCaseImpl(get()) }
    factory<GetStoriesUseCase> { GetStoriesUseCaseImpl(get()) }
    factory<GetHeaderLogoUseCase> { GetHeaderLogoUseCaseImpl(get()) }
    factory<GetBACSDetailsUseCase> { GetBACSDetailsUseCaseImpl(get()) }
    factory<ReviewCriteriaUseCase> { ReviewCriteriaUseCaseImpl(get()) }
    factory<GetPrivacyPolicyUseCase> { GetPrivacyPolicyUseCaseImpl(get()) }
    factory<ForcedEnabledPaymentUseCase> { ForcedEnabledPaymentUseCaseImpl(get()) }
    factory<GetVersionsUseCase> { GetVersionsUseCaseImpl(get()) }
    factory<GetContactInfoUseCase> { GetContactInfoUseCaseImpl(get()) }
}
