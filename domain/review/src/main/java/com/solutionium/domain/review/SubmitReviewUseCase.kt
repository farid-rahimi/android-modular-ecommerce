package com.solutionium.domain.review

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.NewReview
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.Review

interface SubmitReviewUseCase {

    suspend operator fun invoke(review: NewReview): Result<Review, GeneralError>

}