package com.solutionium.data.api.woo.converters

import com.solutionium.data.model.CriteriaRating
import com.solutionium.data.model.NewReview
import com.solutionium.data.model.Review
import com.solutionium.data.model.ReviewChild
import com.solutionium.data.network.request.ReviewRequest
import com.solutionium.data.network.response.CriteriaRatingResponse
import com.solutionium.data.network.response.ReviewChildResponse
import com.solutionium.data.network.response.WooReviewResponse

fun WooReviewResponse.toModel() = Review(
    id = id,
    dateCreated = dateCreated,
    productID = productID,
    productName = productName,
    productPermalink = productPermalink,
    status = status,
    reviewer = reviewer,
    reviewerEmail = reviewerEmail,
    review = review,
    rating = rating,
    verified = verified,
    criteriaRatings = criteriaRatings.map { it.toModel() },
    children = children.map { it.toModel() }
)

fun CriteriaRatingResponse.toModel() = CriteriaRating(
    label = label,
    value = value
)

fun ReviewChildResponse.toModel() = ReviewChild(
    id = id,
    author = author,
    content = content,
    date = date,
    avatar = avatar
)

fun NewReview.toRequestBody() = ReviewRequest(
    productID = productID,
    status = "hold",
    reviewer = reviewer,
    reviewerEmail = reviewerEmail,
    review = review,
    rating = rating,
    criteriaRatings = criteriaRatings.map {
        CriteriaRatingResponse(
            label = it.label,
            value = it.value
        )
    }
)