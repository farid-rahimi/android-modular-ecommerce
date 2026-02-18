package com.solutionium.feature.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionium.data.model.Link
import com.solutionium.data.model.ProductListType
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result
import com.solutionium.data.model.toCartItem
import com.solutionium.domain.cart.AddToCartUseCase
import com.solutionium.domain.cart.ObserveCartUseCase
import com.solutionium.domain.cart.UpdateCartItemUseCase
import com.solutionium.domain.config.GetContactInfoUseCase
import com.solutionium.domain.config.GetHeaderLogoUseCase
import com.solutionium.domain.config.GetStoriesUseCase
import com.solutionium.domain.config.GetVersionsUseCase
import com.solutionium.domain.config.HomeBannersUseCase
import com.solutionium.domain.config.PaymentMethodDiscountUseCase
import com.solutionium.domain.favorite.ObserveFavoritesUseCase
import com.solutionium.domain.favorite.ToggleFavoriteUseCase
import com.solutionium.domain.user.AddStoryViewUseCase
import com.solutionium.domain.user.CheckLoginUserUseCase
import com.solutionium.domain.user.CheckSuperUserUseCase
import com.solutionium.domain.user.GetAllStoryViewUseCase
import com.solutionium.domain.woo.categories.GetCategoryListUseCase
import com.solutionium.domain.woo.products.GetProductsListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface HomeNavigationEvent {
    data class ToProduct(val productId: Int) : HomeNavigationEvent
    data class ToProductList(val params: Map<String, String>) : HomeNavigationEvent
    data class ToExternalLink(val url: String) : HomeNavigationEvent
}

enum class UpdateType {
    NONE, // No update needed
    RECOMMENDED, // A new version is available, but it's optional
    FORCED // The user's version is too old and they must update
}

data class UpdateInfo(
    val type: UpdateType = UpdateType.NONE,
    val latestVersionName: String = ""
)

class HomeViewModel(

    private val context: Context, // Inject context
    private val getProductsUseCase: GetProductsListUseCase,
    private val getCategoriesUseCase: GetCategoryListUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addToCart: AddToCartUseCase,
    private val updateCartItem: UpdateCartItemUseCase,
    private val observeFavoritesUseCase: ObserveFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val homeBannerUseCase: HomeBannersUseCase,
    private val paymentMethodDiscountUseCase: PaymentMethodDiscountUseCase,
    private val getStoriesUseCase: GetStoriesUseCase,
    private val addStoryViewUseCase: AddStoryViewUseCase,
    private val getAllViewedStories: GetAllStoryViewUseCase,
    private val getHeaderLogoUseCase: GetHeaderLogoUseCase,
    private val checkLoginUserUseCase: CheckLoginUserUseCase,
    private val checkSuperUserUseCase: CheckSuperUserUseCase,
    private val getVersionsUseCase: GetVersionsUseCase,
    private val getContactInfoUseCase: GetContactInfoUseCase

    ) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState(isLoading = false))
    val state = _state.asStateFlow()

    private val _bannerState = MutableStateFlow(BannerSliderState(isLoading = false))
    val bannerState = _bannerState.asStateFlow()

    // ▼▼▼ NEW STATE FOR IN-SESSION VIEWED STORIES ▼▼▼
    private val sessionViewedStories = MutableStateFlow<Set<Int>>(emptySet())

    private val _navigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()


    init {

        fetchData()

    }

    private fun fetchData() {
        viewModelScope.launch {
            val headerUrl = getHeaderLogoUseCase()
            _state.update { it.copy(headerLogoUrl = headerUrl) }
        }

        checkSuperUser()

        loadStories()
        observeCart()
        viewModelScope.launch {
            _state.update { it.copy(newArrivalsLoading = true) }
            loadProducts(ProductListType.New) { result ->
                _state.update { it.copy(newArrivals = result, newArrivalsLoading = false) }
            }

            _state.update { it.copy(appOffersLoading = true) }
            loadProducts(ProductListType.Offers) { result ->
                _state.update { it.copy(appOffers = result, appOffersLoading = false) }
            }


            _state.update { it.copy(featuredLoading = true) }
            loadProducts(ProductListType.Features) { result ->
                _state.update { it.copy(featured = result, featuredLoading = false) }
            }

            _state.update { it.copy(onSalesLoading = true) }
            loadProducts(ProductListType.OnSale) { result ->
                _state.update { it.copy(onSales = result, onSalesLoading = false) }
            }
        }
        loadBanners()
        observeFavorites()
        loadPaymentMethodDiscounts()
        observeSession()

        checkAppVersion()
        loadContactInfo()
    }

    private fun checkAppVersion() {
        viewModelScope.launch {
            try {
                // 1. Fetch remote config
                val config =
                    getVersionsUseCase() ?: return@launch // This should return your config object



                val latestVersionName = config.latestVersion
                val minimumVersionName = config.minRequiredVersion

                // 2. Get current app version
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                val currentVersionName = packageInfo.versionName ?: return@launch

                // 3. Compare versions and determine update type
                val updateType = when {
                    // Simple string comparison works for versions like "1.9.0" vs "1.10.0"
                    currentVersionName < minimumVersionName -> UpdateType.FORCED
                    currentVersionName < latestVersionName -> UpdateType.RECOMMENDED
                    else -> UpdateType.NONE
                }

                // 4. Update the UI state
                if (updateType != UpdateType.NONE) {
                    _state.update {
                        it.copy(
                            updateInfo = UpdateInfo(
                                type = updateType,
                                latestVersionName = latestVersionName
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions, e.g., network error or parsing error
                // Silently fail is often okay here
            }
        }
    }

    private fun loadContactInfo() {
        viewModelScope.launch {
            val contactInfo = getContactInfoUseCase()
            _state.update { it.copy(contactInfo = contactInfo) }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // Re-run all the initial data fetching logic
            fetchData()
            // A small delay can make the UI feel smoother
            delay(600)
            _isRefreshing.value = false
        }
    }

    fun dismissUpdateDialog() {
        _state.update { it.copy(updateInfo = it.updateInfo.copy(type = UpdateType.NONE)) }
    }

    fun showContactSupport() {
        _state.update { it.copy(showContactSupportDialog = true) }
    }

    fun dismissContactSupport() {
        _state.update { it.copy(showContactSupportDialog = false) }
    }

    private fun observeSession() {
        viewModelScope.launch {
            checkLoginUserUseCase().collect { result ->
                _state.update { it.copy(isLogin = result) }
//                if (result)
//                    observeFavorites()
            }

        }
    }

    private fun checkSuperUser() {
        viewModelScope.launch {

            val isSuperUser = checkSuperUserUseCase().first()
            _state.update { it.copy(isSuperUser = isSuperUser) }

        }

    }


    private fun loadStories() {

        viewModelScope.launch {
            _state.update { it.copy(storiesLoading = true) }
            val serverStories = getStoriesUseCase()
            if (serverStories.isNotEmpty()) {
                _state.update { it.copy(serverStoryItems = serverStories, storiesLoading = false) }
                observeViewedStories()
            }

        }

    }

    private fun observeViewedStories() {
        viewModelScope.launch {
            getAllViewedStories().collect { viewedItems ->
                val viewedIds = viewedItems.map { it.storyId }.toSet()
                val originalStories = _state.value.serverStoryItems

                // 2. Use `map` to create NEW StoryItem objects. This is crucial.
                // This prevents mutating the state from the previous run.
                val storiesWithUpdatedFlag = originalStories.map { story ->
                    story.copy(isViewed = story.id in viewedIds)
                }
                val (unread, read) = storiesWithUpdatedFlag.partition { !it.isViewed }
//                val (unread, read) = stories.partition { story ->
//                    story.id !in viewedIds
//                }
                //read.onEach { it.isViewed = true }
                val viewedTimeMap = viewedItems.associateBy({ it.storyId }, { it.viewedAt })
                // Sort the 'read' list by the timestamp from the map, most recent first.
                val sortedRead = read.sortedByDescending { viewedTimeMap[it.id] }

                _state.update { state ->
                    state.copy(storyItems = (unread + sortedRead))
                }
            }
        }
    }

    // RENAMED: This function now only updates the in-memory set.
    fun setStoryAsViewedInSession(storyId: Int) {
        sessionViewedStories.update { it + storyId }
    }


    fun persistViewedStories() {
        viewModelScope.launch {
            sessionViewedStories.value.forEach { id ->
                addStoryViewUseCase(id)
            }
            // Clear the temporary set after saving to the database.
            sessionViewedStories.value = emptySet()
        }
    }


    private fun loadPaymentMethodDiscounts() {
        viewModelScope.launch {
            val discounts = paymentMethodDiscountUseCase()
            _state.update { it.copy(paymentDiscount = discounts.values.maxOrNull()) }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            observeFavoritesUseCase()
                .collect { favoriteIds ->
                    _state.update { it.copy(favoriteIds = favoriteIds) }
                }

        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            observeCartUseCase().collect { cartItems ->
                _state.update { currentState ->
                    currentState.copy(cartItems = cartItems)
                }
            }
        }
    }


    private fun loadBanners() {
        viewModelScope.launch {
            _bannerState.value = _bannerState.value.copy(isLoading = true)
            val banners = homeBannerUseCase()
            _bannerState.value = _bannerState.value.copy(banners = banners, isLoading = false)
        }
    }

    private suspend fun loadCategories() {
        getCategoriesUseCase().onStart { _state.update { state -> state.copy(isLoading = true) } }
            .onCompletion { _state.update { state -> state.copy(isLoading = false) } }
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { state ->
                            state.copy(
                                //sectionItems = state.sectionItems + listOf(
                                //CategorySection("Cats", result.data)
                                //)
                            )
                        }
                    }

                    is Result.Failure -> {
                        //delay(2000)
                    }
                }
            }.collect()
    }

    private suspend fun loadProducts(
        listType: ProductListType,
        onResult: (List<ProductThumbnail>) -> Unit
    ) {

        getProductsUseCase(listType).collect { result ->
            when (result) {
                is Result.Success -> {
                    onResult(result.data)
                }

                is Result.Failure -> {
                    onResult(emptyList())
                }
            }
        }

    }


    fun addToCart(product: ProductThumbnail) {
        viewModelScope.launch {
            val existingItemCount = state.value.cartItemCount(product.id)
            if (existingItemCount > 0) {
                updateCartItem.increaseCartItemQuantity(product.id)
            } else addToCart(product.toCartItem())
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            updateCartItem.decreaseCartItemQuantity(productId)
        }
    }

    fun toggleFavorite(productId: Int, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {
//            if (_state.value.isLogin) {
//                // User is logged in, proceed as normal
//                toggleFavoriteUseCase(productId, isCurrentlyFavorite)
//            } else {
//                // User is not logged in, update the state to show the prompt
//                GlobalUiState.showLoginPrompt()
//            }
            toggleFavoriteUseCase(productId, isCurrentlyFavorite)
        }
    }

    fun onLinkClick(link: Link) {
        viewModelScope.launch {
            link.let { link ->
                when {
                    link.isProductLink -> {
                        val productId = link.target.toIntOrNull() ?: return@launch
                        _navigationEvent.emit(HomeNavigationEvent.ToProduct(productId))
                    }
                    link.isProductsLink -> {
                        _navigationEvent.emit(HomeNavigationEvent.ToProductList(link.getRouteQuery()))
                    }
                    link.isExternalLink -> {
                        link.target.let { url ->
                            _navigationEvent.emit(HomeNavigationEvent.ToExternalLink(url))
                        }
                    }
                }
            }
        }
    }

}