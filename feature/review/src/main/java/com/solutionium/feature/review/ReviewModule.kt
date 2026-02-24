package com.solutionium.feature.review

import com.solutionium.shared.domain.config.getConfigDomainModules
import com.solutionium.shared.domain.review.getReviewDomainModules
import com.solutionium.domain.user.getUserDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getReviewModules() = setOf(reviewModule) + getReviewDomainModules() + getUserDomainModules() + getConfigDomainModules()

val reviewModule = module {
    viewModel {
        ReviewViewModel(
            savedStateHandle = get(),
            getReviewListPaging = get(),
            submitReviewUseCase = get(),
            checkLoginUserUseCase = get(),
            getCurrentUserUseCase = get(),
            reviewCriteriaUseCase = get()
        )
    }
}
