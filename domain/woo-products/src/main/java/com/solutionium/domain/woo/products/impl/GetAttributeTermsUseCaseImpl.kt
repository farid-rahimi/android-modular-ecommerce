package com.solutionium.domain.woo.products.impl

import com.solutionium.shared.data.model.AttributeTermsListType
import com.solutionium.shared.data.products.WooProductRepository
import com.solutionium.domain.woo.products.GetAttributeTermsUseCase
import kotlinx.coroutines.flow.flow

internal class GetAttributeTermsUseCaseImpl(
    private val wooProductRepository: WooProductRepository
) : GetAttributeTermsUseCase {
    override suspend fun invoke(listType: AttributeTermsListType) = flow {
        emit(wooProductRepository.getAttributeTerms(listType))
    }

}