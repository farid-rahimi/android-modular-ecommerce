package com.solutionium.shared.domain.review

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.NewReview
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.Review
import com.solutionium.shared.data.products.WooProductRepository

class SubmitReviewUseCaseImpl(

    private val productRepository: WooProductRepository

) : SubmitReviewUseCase {
    override suspend fun invoke(review: NewReview): Result<Review, GeneralError> =
        productRepository.submitReview(review)
}