package com.solutionium.domain.review

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewReview
import com.solutionium.data.model.Result
import com.solutionium.data.model.Review

interface SubmitReviewUseCase {

    suspend operator fun invoke(review: NewReview): Result<Review, GeneralError>

}