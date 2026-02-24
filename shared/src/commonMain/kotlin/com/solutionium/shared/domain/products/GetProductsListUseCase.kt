package com.solutionium.shared.domain.products

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductListType
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetProductsListUseCase {

    suspend operator fun invoke(productListType: ProductListType): Flow<Result<List<ProductThumbnail>, GeneralError>>
}