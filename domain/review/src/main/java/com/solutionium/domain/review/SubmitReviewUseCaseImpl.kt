package com.solutionium.domain.review

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewReview
import com.solutionium.data.model.Result
import com.solutionium.data.model.Review
import com.solutionium.data.woo.products.WooProductRepository

class SubmitReviewUseCaseImpl(

    private val productRepository: WooProductRepository

) : SubmitReviewUseCase {
    override suspend fun invoke(review: NewReview): Result<Review, GeneralError> =
        productRepository.submitReview(review)
}