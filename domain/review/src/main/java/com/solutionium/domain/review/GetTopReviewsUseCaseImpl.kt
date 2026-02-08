package com.solutionium.domain.review

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.Review
import com.solutionium.data.woo.products.WooProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopReviewsUseCaseImpl @Inject constructor(
    private val productRepository: WooProductRepository

) : GetTopReviewsUseCase {
    override suspend fun invoke(productId: Int): Flow<Result<List<Review>, GeneralError>> = flow {
        emit(productRepository.getReviewList(
            mapOf(
                "product" to productId.toString(),
            )
        ))
    }

}