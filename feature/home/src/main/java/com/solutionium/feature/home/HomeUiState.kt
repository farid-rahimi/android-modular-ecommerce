package com.solutionium.feature.home

import com.solutionium.shared.data.model.BannerItem
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.model.ContactInfo
import com.solutionium.shared.data.model.ProductListType
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.StoryItem

data class HomeUiState(
    val updateInfo: UpdateInfo = UpdateInfo(), // Add this
    val contactInfo: ContactInfo? = null, // Add this state
    val showContactSupportDialog: Boolean = false, // Add this state

    val isLoading: Boolean,
    val headerLogoUrl: String? = null,
    val serverStoryItems: List<StoryItem> = emptyList(), // <-- ADDED THIS
    val storiesLoading: Boolean = true,
    val storyItems: List<StoryItem> = emptyList(), // <-- ADDED THIS
    //val sectionItems: List<HomeSection> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val favoriteIds: List<Int> = emptyList(),
    val paymentDiscount: Double? = null,

    val newArrivals: List<ProductThumbnail> = emptyList(),
    val newArrivalsLoading: Boolean = true,

    val appOffers: List<ProductThumbnail> = emptyList(),
    val appOffersLoading: Boolean = true,

    val featured: List<ProductThumbnail> = emptyList(),
    val featuredLoading: Boolean = true,

    val onSales: List<ProductThumbnail> = emptyList(),
    val onSalesLoading: Boolean = true,

    val isLogin: Boolean = false,
    val isSuperUser: Boolean = false


    ) {
    fun cartItemCount(productId: Int): Int =
        cartItems.find { it.productId == productId && it.variationId == 0}?.quantity ?: 0

    fun isFavorite(productId: Int): Boolean =
        favoriteIds.contains(productId)

    fun discountedPrice(originalPrice: Double?): Double? =
        originalPrice?.let { paymentDiscount?.let { (100 - it) / 100 }?.let { it * originalPrice }}

}


data class BannerSliderState(
    val banners: List<BannerItem> = emptyList(),
    val isLoading: Boolean = false
)

