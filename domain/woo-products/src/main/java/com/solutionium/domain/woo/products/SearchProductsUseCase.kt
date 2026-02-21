package com.solutionium.domain.woo.products

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface SearchProductsUseCase {

    operator fun invoke(query: String) : Flow<Result<List<ProductThumbnail>, GeneralError>>

}