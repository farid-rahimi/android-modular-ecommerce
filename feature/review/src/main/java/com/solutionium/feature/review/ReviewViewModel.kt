package com.solutionium.feature.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.solutionium.shared.data.model.CriteriaRating
import com.solutionium.shared.data.model.FilterCriterion
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.NewReview
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.UserDetails
import com.solutionium.domain.config.ReviewCriteriaUseCase
import com.solutionium.domain.review.GetReviewListPagingUseCase
import com.solutionium.domain.review.SubmitReviewUseCase
import com.solutionium.domain.user.CheckLoginUserUseCase
import com.solutionium.domain.user.GetCurrentUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// State holder for the new review form
data class ReviewFormState(
    val rating: Int = 0,
    val reviewText: String = "",
    val criteriaRatings: Map<String, Int> = emptyMap(), // Map of criteria label to rating
    val isSubmitting: Boolean = false,
    val checkingLogin: Boolean = false,
    val loadingUser: Boolean = false,
    val isLoggedIn: Boolean = false,
    val userDetails: UserDetails? = null,
)

class ReviewViewModel(
    savedStateHandle: SavedStateHandle,
    getReviewListPaging: GetReviewListPagingUseCase, // Inject your repository
    private val submitReviewUseCase: SubmitReviewUseCase,
    private val checkLoginUserUseCase: CheckLoginUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val reviewCriteriaUseCase: ReviewCriteriaUseCase
) : ViewModel() {


    private val productId: Int =
        savedStateHandle["productId"] ?: throw IllegalArgumentException("Product ID is required")

    private val categoryIds: List<Int> =
        savedStateHandle.get<String>("categoryIds") // 1. Get the encoded string
            ?.split(",")                             // 2. Split it back into a list of strings
            ?.mapNotNull { it.toIntOrNull() }        // 3. Convert each to an Int, ignoring invalid ones
            ?: emptyList()                           // 4. Default to an empty list if not present


    val reviews = getReviewListPaging(
        listOf(
            FilterCriterion("product", productId.toString())
        )
    ).cachedIn(viewModelScope)

    var showReviewDialog by mutableStateOf(false)
        private set


    private val _state: MutableStateFlow<ReviewFormState> = MutableStateFlow(ReviewFormState())
    val state: StateFlow<ReviewFormState> = _state.asStateFlow()

    // Holds the list of criteria questions, loaded from config
    var productReviewCriteria by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        checkLoginStatus()
    }


    private fun checkLoginStatus() {
        viewModelScope.launch {
            _state.update { it.copy(checkingLogin = true) }
            val isLoggedIn = checkLoginUserUseCase().first() // Assume not logged in initially
            if (isLoggedIn) {
                _state.update { it.copy(isLoggedIn = true, checkingLogin = false) }
                fetchUserDetailsAndOrders()
            } else {
                _state.update { it.copy(isLoggedIn = false, checkingLogin = false) }
            }
        }
    }

    private fun fetchUserDetailsAndOrders() {
        viewModelScope.launch {
            _state.update { it.copy(loadingUser = true) }

            getCurrentUserUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(userDetails = result.data, loadingUser = false) }
                    }

                    is Result.Failure -> {
                        _state.update { it.copy(loadingUser = false) }
                        when (result.error) {
                            is GeneralError.ApiError -> {}
                            GeneralError.NetworkError -> {}
                            is GeneralError.UnknownError -> {}
                        }


                    }
                }
            }
        }
    }




    fun onOpenReviewDialog() {

        if (_state.value.isLoggedIn) {
            // Load the criteria for this product category when the dialog is opened
            loadReviewCriteria()
            showReviewDialog = true
        }

    }

    fun onCloseReviewDialog() {
        showReviewDialog = false
        // Reset state when closing
    }

    fun onRatingChange(newRating: Int) {
        _state.update { it.copy(rating = newRating) }
    }

    fun onReviewTextChange(newText: String) {
        _state.update { it.copy(reviewText = newText) }
    }

    fun onCriteriaRatingChange(criteriaLabel: String, rating: Int) {
        val updatedCriteria = _state.value.criteriaRatings.toMutableMap()
        updatedCriteria[criteriaLabel] = rating
        _state.update { it.copy(criteriaRatings = updatedCriteria) }
    }

    private fun loadReviewCriteria() {
        viewModelScope.launch {
            // Now you can use the categoryIds list!
            if (categoryIds.isNotEmpty()) {
                // Pass the first category ID (or all of them, depending on your use case)
                productReviewCriteria = reviewCriteriaUseCase(categoryIds)
            } else {
                // Handle the case where no category IDs were provided
                productReviewCriteria = emptyList()
            }
        }
    }

//    private fun loadReviewCriteria() {
//        viewModelScope.launch {
//            // In a real app, you would pass a product category ID here
//
//            //productReviewCriteria = configRepository.getReviewCriteriaForProductCategory("some-category-id")
//            productReviewCriteria = listOf("Quality", "Value for Money", "Durability")
//        }
//    }

    fun submitReview() {
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true) }
            //reviewFormState = reviewFormState.copy(isSubmitting = true)
            val reviewFormState = _state.value
            val newReview = NewReview(

                productID = productId,
                reviewer = reviewFormState.userDetails?.displayName ?: "", // Replace with actual user data
                reviewerEmail = reviewFormState.userDetails?.email ?: "app@noemail.io",
                review = reviewFormState.reviewText,
                rating = reviewFormState.rating,
                criteriaRatings = reviewFormState.criteriaRatings.map { (label, value) ->
                    CriteriaRating(label, value)
                }
            )

            val result = submitReviewUseCase(newReview)
            when (result) {
                is Result.Success -> {
                    _state.update { it.copy(isSubmitting = false) }
                    onCloseReviewDialog()
                }
                is Result.Failure -> {
                    _state.update { it.copy(isSubmitting = false) }

                    // Handle error (e.g., show a message to the user)
                }
            }
            // reviewRepository.postReview(newReview)

        }
    }

}


