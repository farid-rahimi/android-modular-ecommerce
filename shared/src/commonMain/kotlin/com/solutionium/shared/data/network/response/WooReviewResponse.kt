package com.solutionium.shared.data.network.response
import kotlinx.serialization.*

typealias WooReviewListResponse = List<WooReviewResponse>

@Serializable
data class WooReviewResponse (
    val id: Int,

    @SerialName("date_created")
    val dateCreated: String,

    @SerialName("date_created_gmt")
    val dateCreatedGmt: String,

    @SerialName("product_id")
    val productID: Int,

    @SerialName("product_name")
    val productName: String,

    @SerialName("product_permalink")
    val productPermalink: String,

    val status: String,
    val reviewer: String,

    @SerialName("reviewer_email")
    val reviewerEmail: String,

    val review: String,
    val rating: Int,
    val verified: Boolean,

    @SerialName("criteria_ratings")
    val criteriaRatings: List<CriteriaRatingResponse>,

    val children: List<ReviewChildResponse>

)

@Serializable
data class CriteriaRatingResponse (
    val label: String,
    val value: Int
)

@Serializable
data class ReviewChildResponse (
    val id: String,
    val author: String,
    val content: String,
    val date: String,
    val avatar: String,
)