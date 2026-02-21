package com.solutionium.domain.woo.products.impl

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductListType
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.Result
import com.solutionium.data.woo.products.WooProductRepository
import com.solutionium.domain.woo.products.GetProductsListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class GetProductsListUseCaseImpl(
    private val wooProductRepository: WooProductRepository
) : GetProductsListUseCase {
    override suspend fun invoke(productListType: ProductListType): Flow<Result<List<ProductThumbnail>, GeneralError>> = flow {
        emit(wooProductRepository.getProductsList(productListType.queries))
    }
}