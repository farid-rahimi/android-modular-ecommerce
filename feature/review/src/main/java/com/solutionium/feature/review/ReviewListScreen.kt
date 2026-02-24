package com.solutionium.feature.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.solutionium.sharedui.common.component.CriteriaRatingBar
import com.solutionium.sharedui.common.component.OrderSummaryCardPlaceholder
import com.solutionium.sharedui.common.component.ReviewItem
import com.solutionium.shared.data.model.Review

@Composable
fun ReviewListScreen(
    viewModel: ReviewViewModel,
    onBackClick: () -> Unit
) {
    val reviews: LazyPagingItems<Review> = viewModel.reviews.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val isRefreshing = reviews.loadState.refresh is LoadState.Loading


    if (viewModel.showReviewDialog) {
        ReviewFormDialog(
            formState = state,
            reviewCriteria = viewModel.productReviewCriteria,
            onDismiss = { viewModel.onCloseReviewDialog() },
            onRatingChange = { viewModel.onRatingChange(it) },
            onReviewTextChange = { viewModel.onReviewTextChange(it) },
            onCriteriaRatingChange = { criteria, rating ->
                viewModel.onCriteriaRatingChange(
                    criteria,
                    rating
                )
            },
            onSubmit = { viewModel.submitReview() }
        )
    }

    val productName = if (reviews.itemCount > 0) reviews.peek(0)?.productName else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(stringResource(R.string.reviews_title))
                        if (!productName.isNullOrBlank()) {
                            Text(
                                text = productName,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                },


                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { if (state.isLoggedIn) viewModel.onOpenReviewDialog() },
                icon = { Icon(Icons.Filled.Edit, contentDescription = "Write a review") },
                text = { if (state.isLoggedIn) Text(stringResource(R.string.write_a_review)) else Text(
                    stringResource(R.string.login_to_review)
                ) }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    ) { paddingValues ->

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { reviews.refresh() } // <-- Call refresh on the Paging items
        ) {
            if (reviews.itemCount == 0 && reviews.loadState.refresh is LoadState.NotLoading) {
                // Show a message when there are no reviews
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_reviews_yet),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                return@PullToRefreshBox
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {

                if (reviews.loadState.refresh is LoadState.Loading) {
                    items(3) { // Display 3 shimmer placeholders
                        OrderSummaryCardPlaceholder()
                    }
                }

                // Display the actual loaded items
                items(reviews.itemCount) { index ->
                    reviews[index]?.let { review ->
                        ReviewItem(
                            review = review,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth()
                        )
                    }
                }

                // Check the append state for pagination loading
                if (reviews.loadState.append is LoadState.Loading) {
                    item { // Display one placeholder at the bottom
                        OrderSummaryCardPlaceholder()
                    }
                }


            }
        }
    }
}

@Composable
fun ReviewFormDialog(
    formState: ReviewFormState,
    reviewCriteria: List<String>,
    onDismiss: () -> Unit,
    onRatingChange: (Int) -> Unit,
    onReviewTextChange: (String) -> Unit,
    onCriteriaRatingChange: (String, Int) -> Unit,
    onSubmit: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier.padding(20.dp)
            ) {
                item {
                    Text(
                        stringResource(R.string.review_form_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))

                    // Overall Star Rating
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        (1..5).forEach { index ->
                            Icon(
                                imageVector = if (index <= formState.rating) Icons.Filled.Star else Icons.Filled.StarOutline,
                                contentDescription = null,
                                tint = if (index <= formState.rating) Color(0xFFFFC107) else MaterialTheme.colorScheme.outline,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable { onRatingChange(index) }
                                    .padding(4.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))

                    // Review Text Field
                    OutlinedTextField(
                        value = formState.reviewText,
                        onValueChange = onReviewTextChange,
                        label = { Text(stringResource(R.string.review_field_label)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        maxLines = 8
                    )
                    Spacer(Modifier.height(16.dp))
                }
                // Dynamic Criteria Rating
                if (reviewCriteria.isNotEmpty()) {
                    item {
                        Text(
                            stringResource(R.string.criteria_ratings_title),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                    items(reviewCriteria) { criteria ->
                        val currentRating = formState.criteriaRatings[criteria] ?: 0
                        CriteriaRatingBar(
                            label = criteria,
                            value = currentRating,
                            isEditable = true,
                            onEditRating = { newRating ->
                                onCriteriaRatingChange(
                                    criteria,
                                    newRating
                                )
                            }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }

                // Submit Button
                item {
                    Spacer(Modifier.height(16.dp))
                    Button(
                        shape = MaterialTheme.shapes.medium,
                        onClick = onSubmit,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = formState.rating > 0 && formState.reviewText.isNotBlank() && !formState.isSubmitting
                    ) {
                        if (formState.isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(stringResource(R.string.submit_review))
                        }
                    }
                }
            }
        }
    }
}

