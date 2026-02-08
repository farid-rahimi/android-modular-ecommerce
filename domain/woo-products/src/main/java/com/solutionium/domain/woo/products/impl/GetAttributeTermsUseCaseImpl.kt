package com.solutionium.domain.woo.products.impl

import com.solutionium.data.model.AttributeTermsListType
import com.solutionium.data.woo.products.WooProductRepository
import com.solutionium.domain.woo.products.GetAttributeTermsUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetAttributeTermsUseCaseImpl @Inject constructor(
    private val wooProductRepository: WooProductRepository
) : GetAttributeTermsUseCase {
    override suspend fun invoke(listType: AttributeTermsListType) = flow {
        emit(wooProductRepository.getAttributeTerms(listType))
    }

}