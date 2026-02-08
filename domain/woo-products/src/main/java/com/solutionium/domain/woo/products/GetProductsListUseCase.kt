package com.solutionium.domain.woo.products

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductListType
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetProductsListUseCase {

    suspend operator fun invoke(productListType: ProductListType): Flow<Result<List<ProductThumbnail>, GeneralError>>
}