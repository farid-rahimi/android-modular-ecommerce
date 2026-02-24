package com.solutionium.domain.woo.products.impl

import com.solutionium.shared.data.model.BrandListType
import com.solutionium.shared.data.products.WooProductRepository
import com.solutionium.domain.woo.products.GetBrandsUseCase
import kotlinx.coroutines.flow.flow

internal class GetBrandsUseCaseImpl(
    private val wooProductRepository: WooProductRepository
) : GetBrandsUseCase {
    override suspend fun invoke(type: BrandListType) = flow{
        emit(wooProductRepository.getBrandList(type))
    }

}