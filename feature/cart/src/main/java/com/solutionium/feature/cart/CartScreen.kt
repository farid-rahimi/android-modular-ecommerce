package com.solutionium.feature.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solutionium.core.ui.common.LoginPromptDialog
import com.solutionium.core.ui.common.component.CartItemCard
import com.solutionium.core.ui.common.component.PriceView
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.UiText
import com.solutionium.shared.data.model.ValidationInfo
import kotlin.collections.toTypedArray

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    onCheckoutClick: () -> Unit,
    onProductClick: (id: Int) -> Unit,
    onNavigateToAccount: () -> Unit, // Add this new navigation callback
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val isRefreshing by viewModel.isRefreshing.collectAsState() // <-- Collect the refreshing state


    if (state.showLoginPrompt) {
        LoginPromptDialog(
            onDismiss = viewModel::dismissLoginPrompt,
            onConfirm = {
                viewModel.dismissLoginPrompt() // Dismiss first
                onNavigateToAccount() // Then navigate
            }
        )
    }


    //LaunchedEffect() { }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // The screen has become visible to the user.
                // Call the validation function in the ViewModel.
                //println("CartScreen RESUMED, triggering validation.")
                viewModel.validateCart()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refresh() }
    ) {
        CartScreenContent(
            state = state,
            onCheckoutClick = {
                viewModel.onCheckoutClick(
                    onNavigateToCheckout = onCheckoutClick
                )

//            if (state.isUserLoggedIn) {
//                onCheckoutClick() // Navigate directly if logged in
//            } else {
//                viewModel.onCheckoutClick() // Show prompt if not logged in
//            }
            },
            onProductClick = onProductClick,
            onRemove = { viewModel.removeItem(it) },
            onIncreaseQuantity = { viewModel.increaseQuantity(it) },
            onDecreaseQuantity = { viewModel.decreaseQuantity(it) },
            onConfirmValidation = { viewModel.confirmCartValidation() },
            infoMapper = viewModel::mapValidationInfoToUiText
        )
    }


}


@Composable
fun CartScreenContent(
    state: CartScreenUiState,
    onProductClick: (id: Int) -> Unit,
    onCheckoutClick: () -> Unit,
    onRemove: (CartItem) -> Unit,
    onIncreaseQuantity: (CartItem) -> Unit,
    onDecreaseQuantity: (CartItem) -> Unit,
    onConfirmValidation: () -> Unit,
    infoMapper: (ValidationInfo?) -> UiText?
) {

//    Box(
//        modifier = Modifier.fillMaxSize(),
//    ) {

    Column(modifier = Modifier.fillMaxSize()) {
        // Validation Summary
        if (state.cartItems.isNotEmpty())
            ValidationSummary(
                summaryId = state.validationSummaryId,
                error = state.validationError
            )

        Box(modifier = Modifier.weight(1f)) {
            if (state.isPerformingValidation) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            stringResource(R.string.validating_cart_items),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else if (state.cartItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(R.string.empty_cart),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(
                        state.cartItems,
                        key = { intArrayOf(it.productId, it.variationId) }) { item ->
                        CartItemCard(
                            cartItem = item,
                            validationMessage = infoMapper(item.validationInfo)?.asString(),
                            onProductClick = { onProductClick(item.productId) },
                            discountedPrice = state::discountedPrice,
                            onRemove = { onRemove(item) },
                            onIncreaseQuantity = { onIncreaseQuantity(item) },
                            onDecreaseQuantity = { onDecreaseQuantity(item) }
                        )
                    }
                }
            }

        }
        if (!state.isPerformingValidation && state.cartItems.isNotEmpty()) {
            CartBottomBar(
                totalPrice = state.totalPrice,
                hasAttentionItems = state.hasAttentionItems,
                discountedPrice = state::discountedPrice,
                onConfirmValidation = onConfirmValidation,
                onCheckoutClick = onCheckoutClick
            )
        }
    }


//            Row {
//                Button(
//                    onClick = onCheckoutClick,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                        //.align(Alignment.BottomCenter),
//                ) {
//                    Text(text = "Checkout")
//                }
//                Button(
//                    onClick = onValidateClick,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                    //.align(Alignment.BottomCenter),
//                ) {
//                    Text(text = "Validate")
//                }
//            }


    //}


}


@Composable
fun CartBottomBar(
    modifier: Modifier = Modifier,
    totalPrice: Double,
    hasAttentionItems: Boolean,
    discountedPrice: (Double?) -> Double? = { null },
    onConfirmValidation: () -> Unit,
    onCheckoutClick: () -> Unit
) {
    Surface(shadowElevation = 8.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (hasAttentionItems) {
                Text(
                    stringResource(R.string.review_the_changes_before_proceeding),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "قسطی",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(" x 4 ", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                        PriceView(
                            totalPrice / 4,
                            false,
                            null,
                            magnifier = 1.5
                        )
                    }
                    discountedPrice(totalPrice)?.let {
                        Row {
                            Text(
                                text = "نقدی",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            PriceView(it, false, null, magnifier = 1.0)
                        }
                    }
                }
//                FormattedPriceV2(
//                    amount = totalPrice.toLong(),
//                    magnifier = 1.5
//                )

                Button(
                    onClick = if (hasAttentionItems) onConfirmValidation else onCheckoutClick,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .defaultMinSize(minWidth = 140.dp),
                    // Disable checkout if items need review; user must re-validate first.
                    //enabled = !hasAttentionItems
                ) {
                    Text(if (hasAttentionItems) stringResource(R.string.confirm_updates) else stringResource(
                        R.string.proceed_to_checkout
                    )
                    )
                }
            }
        }
    }
}


@Composable
fun ValidationSummary(summaryId: Int?, error: String?) {
    val message = if (summaryId != null) stringResource(summaryId) else null
    if (message != null) {
        val isError = error != null
        InfoBox(
            message = message,
            icon = if (isError) Icons.Default.Warning else Icons.Outlined.Info,
            color = if (isError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun InfoBox(
    message: String,
    icon: ImageVector,
    color: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = contentColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = message, color = contentColor, style = MaterialTheme.typography.bodyMedium)
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> {
            val context = androidx.compose.ui.platform.LocalContext.current
            // The resId is now an Int, so we can use it directly
            // No need for getIdentifier, which is slower and less safe
            if (this.args.isEmpty()) {
                context.getString(this.resId)
            } else {
                context.getString(this.resId, *this.args.toTypedArray())
            }
        }
    }
}




