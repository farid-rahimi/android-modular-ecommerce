package com.solutionium.domain.woo.products

import com.solutionium.shared.data.model.AttributeTerm
import com.solutionium.shared.data.model.Brand
import com.solutionium.shared.data.model.BrandListType
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetBrandsUseCase {
    suspend operator fun invoke(type: BrandListType): Flow<Result<List<Brand>, GeneralError>>
}