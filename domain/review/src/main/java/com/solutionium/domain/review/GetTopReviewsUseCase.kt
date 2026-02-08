package com.solutionium.domain.review

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.Review
import kotlinx.coroutines.flow.Flow

interface GetTopReviewsUseCase {

    suspend operator fun invoke(productId: Int): Flow<Result<List<Review>, GeneralError>>

}