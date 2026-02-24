package com.solutionium.shared.domain.review

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.Review
import kotlinx.coroutines.flow.Flow

interface GetTopReviewsUseCase {

    suspend operator fun invoke(productId: Int): Flow<Result<List<Review>, GeneralError>>

}