package com.solutionium.domain.woo.products

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result
import kotlinx.coroutines.flow.Flow

interface SearchProductsUseCase {

    operator fun invoke(query: String) : Flow<Result<List<ProductThumbnail>, GeneralError>>

}