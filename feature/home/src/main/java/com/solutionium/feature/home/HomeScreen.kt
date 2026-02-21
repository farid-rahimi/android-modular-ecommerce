package com.solutionium.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solutionium.core.ui.common.component.BannerSlider
import com.solutionium.core.ui.common.component.CategoryThumbnailCard
import com.solutionium.core.ui.common.component.ContactSupportDialog
import com.solutionium.core.ui.common.component.HeaderLogo
import com.solutionium.core.ui.common.component.ProductCarouselPlaceholder
import com.solutionium.core.ui.common.component.ProductThumbnailCard
import com.solutionium.core.ui.common.component.StoryReelPlaceholder
import com.solutionium.core.ui.common.component.StoryReelSection
import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.model.PRODUCT_ARG_FEATURED
import com.solutionium.shared.data.model.PRODUCT_ARG_ON_SALE
import com.solutionium.shared.data.model.PRODUCT_ARG_TAG
import com.solutionium.shared.data.model.PRODUCT_ARG_TITLE
import com.solutionium.shared.data.model.ProductListType
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.StoryItem

@Composable
fun HomeScreen(
    onProductClick: (Int) -> Unit,
    navigateToProductList: (params: Map<String, String>) -> Unit = {},
    onStoryClick: (StoryItem) -> Unit,
    viewModel: HomeViewModel
) {


    val state by viewModel.state.collectAsStateWithLifecycle()
    val bannerState by viewModel.bannerState.collectAsState()

    val isRefreshing by viewModel.isRefreshing.collectAsState() // <-- Collect the refreshing state

//    LaunchedEffect(Unit) {
//        viewModel.navigationEvent.collect { event ->
//            when (event) {
//                is HomeNavigationEvent.ToProduct -> onProductClick(event.productId)
//                is HomeNavigationEvent.ToProductList -> navigateToProductList(event.params)
//                is HomeNavigationEvent.ToExternalLink -> {
//                    try {
//                        val intent = Intent(Intent.ACTION_VIEW, event.url.toUri())
//                        context.startActivity(intent)
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
//            }
//        }
//    }

    Box(modifier = Modifier.fillMaxSize()) {

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refresh() }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // Ensure LazyColumn fills the box
                contentPadding = PaddingValues(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {


                state.headerLogoUrl?.let {
                    item {
                        Spacer(modifier = Modifier.height(18.dp))
                        HeaderLogo(it, Modifier.height(32.dp))
                    }
                }

                if (state.storyItems.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(18.dp))
                        if (state.storiesLoading) {
                            StoryReelPlaceholder()
                        } else if (state.storyItems.isNotEmpty()) {
                            StoryReelSection(
                                stories = state.storyItems,
                                onStoryClick = onStoryClick
                            )
                        }
                        Spacer(modifier = Modifier.height(1.dp))
                    }
                }

                item {
                    if (bannerState.isLoading) {
                        // Show a loading shimmer or placeholder for the banner area
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color.LightGray)
                        )
                    } else {
                        BannerSlider(
                            items = bannerState.banners,
                            onBannerClick = { banner ->
                                banner.link?.let {
                                    viewModel.onLinkClick(it)
                                }

                            },
                            modifier = Modifier.padding(top = 8.dp) // Some top padding
                        )
                    }
                }

                item {
                    if (state.newArrivalsLoading)
                        ProductCarouselPlaceholder()
                    else {
                        val title = stringResource(R.string.new_arrivals)
                        ProductSectionRow(
                            title = title,
                            items = state.newArrivals,
                            toggleFavorite = viewModel::toggleFavorite,
                            isFavorite = state::isFavorite,
                            discountedPrice = state::discountedPrice,
                            cartCounter = state::cartItemCount,
                            onProductClick = onProductClick,
                            onShowMoreProduceClick = {
                                navigateToProductList(mapOf(PRODUCT_ARG_TITLE to title))
                            },
                            onAddToCartClick = viewModel::addToCart,
                            onRemoveFromCartClick = viewModel::removeFromCart,
                            showStock = state.isSuperUser
                        )
                    }
                }

                item {
                    if (state.appOffersLoading)
                        ProductCarouselPlaceholder()
                    else {
                        val title = stringResource(R.string.app_offers)
                        ProductSectionRow(
                            title = title,
                            items = state.appOffers,
                            toggleFavorite = viewModel::toggleFavorite,
                            isFavorite = state::isFavorite,
                            discountedPrice = state::discountedPrice,
                            cartCounter = state::cartItemCount,
                            onProductClick = onProductClick,
                            onShowMoreProduceClick = {
                                navigateToProductList(
                                    mapOf(
                                        PRODUCT_ARG_TITLE to title,
                                        PRODUCT_ARG_TAG to (ProductListType.Offers.queries["tag"]
                                            ?: "")
                                    )
                                )
                            },
                            onAddToCartClick = viewModel::addToCart,
                            onRemoveFromCartClick = viewModel::removeFromCart,
                            showStock = state.isSuperUser
                        )
                    }
                }


                item {
                    if (state.onSalesLoading)
                        ProductCarouselPlaceholder()
                    else if (state.onSales.isNotEmpty()) {
                        val title = stringResource(R.string.on_sales)
                        ProductSectionRow(
                            title = title,
                            items = state.onSales,
                            toggleFavorite = viewModel::toggleFavorite,
                            isFavorite = state::isFavorite,
                            discountedPrice = state::discountedPrice,
                            cartCounter = state::cartItemCount,
                            onProductClick = onProductClick,
                            onShowMoreProduceClick = {
                                navigateToProductList(
                                    mapOf(
                                        PRODUCT_ARG_TITLE to title,
                                        PRODUCT_ARG_ON_SALE to "true"
                                    )
                                )
                            },
                            onAddToCartClick = viewModel::addToCart,
                            onRemoveFromCartClick = viewModel::removeFromCart,
                            showStock = state.isSuperUser
                        )
                    }
                }

                item {
                    if (state.featuredLoading)
                        ProductCarouselPlaceholder()
                    else {
                        val title = stringResource(R.string.featured)
                        ProductSectionRow(
                            title = title,
                            items = state.featured,
                            toggleFavorite = viewModel::toggleFavorite,
                            isFavorite = state::isFavorite,
                            discountedPrice = state::discountedPrice,
                            cartCounter = state::cartItemCount,
                            onProductClick = onProductClick,
                            onShowMoreProduceClick = {
                                navigateToProductList(
                                    mapOf(
                                        PRODUCT_ARG_TITLE to title,
                                        PRODUCT_ARG_FEATURED to "true"
                                    )
                                )
                            },
                            onAddToCartClick = viewModel::addToCart,
                            onRemoveFromCartClick = viewModel::removeFromCart,
                            showStock = state.isSuperUser
                        )
                    }
                }

//        items(state.sectionItems) { homeSection ->
//            if (homeSection is CategorySection) {
//                CategorySectionRow(
//                    title = homeSection.title,
//                    items = homeSection.items,
//                    onCategoryClick = { },
//                    onSectionClick = onSectionClick,
//                )
//            } else if (homeSection is ProductSection) {
//                ProductSectionRow(
//                    title = homeSection.title,
//                    items = homeSection.items,
//                    toggleFavorite = viewModel::toggleFavorite,
//                    isFavorite = state::isFavorite,
//                    discountedPrice = state::discountedPrice,
//                    cartCounter = state::cartItemCount,
//                    onProductClick = onProductClick,
//                    onShowMoreProduceClick = {
//                        onShowMoreProduceClick(homeSection.productListType)
//                    },
//                    onAddToCartClick = viewModel::addToCart,
//                    onRemoveFromCartClick = viewModel::removeFromCart,
//                )
//            }
//        }
            }

        }
        //}

        val updateInfo = state.updateInfo
        val isForcedUpdate = updateInfo.type == UpdateType.FORCED

        AnimatedVisibility(
            visible = isForcedUpdate,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ForcedUpdateScrim()
        }

        if (state.updateInfo.type != UpdateType.NONE) {
            UpdateDialog(
                updateInfo = state.updateInfo,
                onDismiss = { viewModel.dismissUpdateDialog() },
                onContactSupportClick = { viewModel.showContactSupport() } // Trigger the new state
            )
        }
        if (state.showContactSupportDialog && state.contactInfo != null) {
            ContactSupportDialog(
                contactInfo = state.contactInfo, // Pass the config from your state
                onDismiss = { viewModel.dismissContactSupport() }
            )
        }
    }
}


@Composable
fun ProductSectionRow(
    title: String,
    items: List<ProductThumbnail>,
    toggleFavorite: (Int, Boolean) -> Unit = { _, _ -> },
    discountedPrice: (Double?) -> Double? = { null },
    isFavorite: (Int) -> Boolean = { false },
    cartCounter: (Int) -> Int = { 0 },
    onProductClick: (Int) -> Unit,
    onShowMoreProduceClick: () -> Unit,
    onAddToCartClick: (ProductThumbnail) -> Unit = {},
    onRemoveFromCartClick: (Int) -> Unit = {},
    showStock: Boolean = false

) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onShowMoreProduceClick() },
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onShowMoreProduceClick() },

                ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowRight,
                    contentDescription = "More",
                    //modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }

        LazyRow(
            modifier = Modifier
                .height(390.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items) { item ->
                Modifier
                    .testTag("discover_carousel_item")

                ProductThumbnailCard(
                    product = item,
                    onProductClick = { onProductClick(item.id) },
                    onFavoriteClick = toggleFavorite,
                    isFavorite = isFavorite(item.id),
                    discountedPrice = discountedPrice,
                    modifier = Modifier
                        .animateItem()
                        .fillParentMaxHeight()
                        .aspectRatio(0.49f),

                    inCartQuantity = cartCounter(item.id),
                    maxQuantity = if (item.manageStock) item.stock else 12,
                    onAddToCartClick = { onAddToCartClick(item) },
                    onRemoveFromCartClick = onRemoveFromCartClick,
                    priceMagnifier = 0.8,
                    showStock = showStock
                )

            }
        }
    }
}

// At the bottom of HomeScreen.kt

@Composable
private fun ForcedUpdateScrim(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.8f))
            .clickable(
                enabled = true,
                // Block all clicks behind the scrim
                onClick = {},
                indication = null, // No ripple effect
                interactionSource = remember { MutableInteractionSource() }
            )
    )
}


@Composable
fun CategorySectionRow(
    title: String,
    items: List<Category>,
    onCategoryClick: (Int) -> Unit,
    onSectionClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSectionClick() },
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        LazyRow(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items) { item ->
                Modifier
                    .testTag("discover_carousel_item")
                CategoryThumbnailCard(
                    modifier = Modifier
                        .animateItem()
                        .fillParentMaxHeight()
                        .aspectRatio(2 / 3f),
                    category = item,
                    onClick = {
                        onCategoryClick(item.id)
                    },
                )
            }
        }
    }
}


@Composable
fun LoadingVideoSectionRow(
    numberOfSections: Int,
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(numberOfSections) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(50.dp, 20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(ShimmerBrush(showShimmer = false)),
            )
            LazyRow(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(10) {
                    Box(
                        modifier = Modifier
                            .fillParentMaxHeight()
                            .aspectRatio(2 / 3f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(ShimmerBrush(showShimmer = false)),

                        )
                }
            }
        }
    }
}




