package com.solutionium.domain.woo.products.impl

import com.solutionium.data.model.BrandListType
import com.solutionium.data.woo.products.WooProductRepository
import com.solutionium.domain.woo.products.GetBrandsUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetBrandsUseCaseImpl @Inject constructor(
    private val wooProductRepository: WooProductRepository
) : GetBrandsUseCase {
    override suspend fun invoke(type: BrandListType) = flow{
        emit(wooProductRepository.getBrandList(type))
    }

}