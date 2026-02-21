package com.solutionium.domain.woo.products

import com.solutionium.shared.data.model.AttributeTerm
import com.solutionium.shared.data.model.AttributeTermsListType
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetAttributeTermsUseCase {

    suspend operator fun invoke(listType: AttributeTermsListType): Flow<Result<List<AttributeTerm>, GeneralError>>

}