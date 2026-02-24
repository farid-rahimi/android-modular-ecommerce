package com.solutionium.feature.category


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionium.shared.data.model.AttributeTerm
import com.solutionium.shared.data.model.AttributeTermsListType
import com.solutionium.shared.data.model.Brand
import com.solutionium.shared.data.model.BrandListType
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.domain.config.GetAppImages
import com.solutionium.domain.user.CheckSuperUserUseCase
import com.solutionium.domain.woo.products.GetAttributeTermsUseCase
import com.solutionium.domain.woo.products.GetBrandsUseCase
import com.solutionium.domain.woo.products.SearchProductsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


enum class CategoryDisplayType {
    MAIN,
    ALL_BRANDS,
    ALL_SCENT_GROUPS,
}

// ViewModel State
data class CategoryScreenState(
    val images: Map<Int, String> = emptyMap(),

    val topScentGroups: List<AttributeTerm> = emptyList(),
    val perfumeSpotlightTerms: List<AttributeTerm> = emptyList(), // e.g. For Him, For Her
    val perfumeSeasonTerms: List<AttributeTerm> = emptyList(), // e.g. For Him, For Her
    val perfumeBrands: List<Brand> = emptyList(),
    val showShoes: Boolean = false,
    val shoeBrands: List<Brand> = emptyList(),
    val allBrands: List<Brand> = emptyList(),
    val allScentGroups: List<AttributeTerm> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val categoryDisplayType: CategoryDisplayType = CategoryDisplayType.MAIN,

    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val searchResults: List<ProductThumbnail> = emptyList(),
    val isSuperUser: Boolean = false

)


class CategoryViewModel(

    //private val categoryRepository: CategoryRepository,
    private val getBrands: GetBrandsUseCase,
    private val getAttributeTerms: GetAttributeTermsUseCase,
    private val getAppImages: GetAppImages,
    private val searchProducts: SearchProductsUseCase, // Add new use case
    private val checkSuperUserUserCase: CheckSuperUserUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryScreenState())
    val uiState = _uiState.asStateFlow()

    private val searchQueryFlow = MutableStateFlow("")

    private var searchJob: Job? = null

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()


    init {
        fetchData()
    }

    private fun fetchData() {
        checkSuperUser()
        loadImages()

        observeSearchQuery() // Start observing the search query

        loadAttributeTerms(AttributeTermsListType.Genders) { terms ->
            _uiState.update { it.copy(perfumeSpotlightTerms = terms) }
        }
        loadBrands(BrandListType.TopPerfumes, true) { brands ->
            _uiState.update { it.copy(perfumeBrands = brands) }
        }

        loadAttributeTerms(AttributeTermsListType.TopScentGroup) { terms ->
            _uiState.update { it.copy(topScentGroups = terms) }
        }

        loadAttributeTerms(AttributeTermsListType.Seasons) { terms ->
            _uiState.update { it.copy(perfumeSeasonTerms = terms) }
        }
        loadBrands(BrandListType.TopShoes) { brands ->
            _uiState.update { it.copy(shoeBrands = brands) }
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

    private fun checkSuperUser() {
        viewModelScope.launch {
            val isLoggedIn = checkSuperUserUserCase().first()
            _uiState.update { it.copy(isSuperUser = isLoggedIn) }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        searchQueryFlow.value = query
    }


    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        searchJob = viewModelScope.launch {
            searchQueryFlow
                // Wait for 300ms of no new input before proceeding
                .debounce(300L)
                // Only proceed if the query is not blank and has changed
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                // Use flatMapLatest to cancel previous search requests
                .flatMapLatest { query ->
                    searchProducts(query) // Assumes this returns a Flow<Result<List<Product>>>
                        .onStart { _uiState.update { it.copy(isSearching = true) } }
                        .onCompletion { _uiState.update { it.copy(isSearching = false) } }
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update { it.copy(searchResults = result.data, isSearching = false) }
                        }
                        is Result.Failure -> {
                            // Handle search failure, maybe show a toast or log it
                            _uiState.update { it.copy(error = result.error.toString(), isSearching = false) }
                        }
                    }
                }
        }
    }

    fun clearSearch() {
        onSearchQueryChanged("")
        _uiState.update { it.copy(searchResults = emptyList()) }
    }


    private fun loadImages() {
        viewModelScope.launch {
            val result = getAppImages()
            _uiState.update { it.copy(images = result) }
        }
    }



    private fun loadBrands(listType: BrandListType, sortByMenuOrder: Boolean = false, onResult: (List<Brand>) -> Unit) {
        viewModelScope.launch {
            getBrands(listType)
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .onCompletion { _uiState.update { it.copy(isLoading = false) } }
                .onEach { result ->
                    when (result) {
                        is Result.Success -> {

                            onResult(result.data.sortedBy { if (sortByMenuOrder) it.menuOrder else 0})
                            //_uiState.update { state -> state.copy(perfumeBrands = result.data.sortedBy { it.menuOrder }) }
                        }

                        is Result.Failure -> {}
                    }
                }
                .collect()
        }
    }

    private fun loadAttributeTerms(
        type: AttributeTermsListType,
        onResult: (List<AttributeTerm>) -> Unit
    ) {
        viewModelScope.launch {
            getAttributeTerms(type)
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .onCompletion { _uiState.update { it.copy(isLoading = false) } }
                .onEach { result ->
                    when (result) {
                        is Result.Success -> {
                            onResult(result.data.sortedBy { it.menuOrder })
                            //uiState = uiState.copy(topScentGroups = result.data.sortedBy { it.menuOrder },)
                        }

                        is Result.Failure -> {}
                    }
                }
                .collect()
        }
    }


    fun backToMainDisplay() {
        _uiState.update { it.copy(categoryDisplayType = CategoryDisplayType.MAIN) }
    }

    fun goToAllBrands() {
        loadBrands(BrandListType.All) { brands ->
            _uiState.update { it.copy(allBrands = brands) }
        }
        _uiState.update { it.copy(categoryDisplayType = CategoryDisplayType.ALL_BRANDS) }
    }

    fun goToAllScentGroups() {
        loadAttributeTerms(AttributeTermsListType.AllScentGroup) { terms ->
            _uiState.update { it.copy(allScentGroups = terms) }
        }
        _uiState.update { it.copy(categoryDisplayType = CategoryDisplayType.ALL_SCENT_GROUPS) }
    }

}


