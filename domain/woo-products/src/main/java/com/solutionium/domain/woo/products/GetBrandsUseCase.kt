package com.solutionium.domain.woo.products

import com.solutionium.data.model.AttributeTerm
import com.solutionium.data.model.Brand
import com.solutionium.data.model.BrandListType
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetBrandsUseCase {
    suspend operator fun invoke(type: BrandListType): Flow<Result<List<Brand>, GeneralError>>
}