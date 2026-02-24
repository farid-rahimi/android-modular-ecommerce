package com.solutionium.shared.data.products

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solutionium.shared.data.api.woo.WooProductsRemoteSource
import com.solutionium.shared.data.model.FilterKey
import com.solutionium.shared.data.model.ProductFilterKey
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.toThrowable

// This PagingSource fetches pages of ProductThumbnail items from the WooProductsRemoteSource
internal class ProductsPagingSource(
    private val queries: Map<String, String>,
    private val wooProductsRemoteSource: WooProductsRemoteSource,
) : PagingSource<Int, ProductThumbnail>() {

    override fun getRefreshKey(state: PagingState<Int, ProductThumbnail>): Int =
        STARTING_PAGE_INDEX

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductThumbnail> = try {
        val page = params.key ?: STARTING_PAGE_INDEX
        when (val response = wooProductsRemoteSource.getProductList(page = page, queries = queries)) {
            is Result.Success -> {
                val movies = response.data
                LoadResult.Page(
                    data = movies,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (movies.size < PAGE_SIZE) null else page + 1,
                )
            }

            is Result.Failure -> LoadResult.Error(response.error.toThrowable())
        }
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = WooProductsRemoteSource.PAGE_SIZE
    }
}



// Key to track which stock status we are fetching and what page we are on.
data class ProductPagingKey(
    val status: String,
    val page: Int
)

private const val TAG = "ProductsPagingSource2"
// This PagingSource fetches products by cycling through stock statuses: "instock", "onbackorder", "outofstock".
class ProductsPagingSource2(
    private val wooProductsRemoteSource: WooProductsRemoteSource,
    private val queries: Map<String, String>,
    //private val getProductListUseCase: GetProductListUseCase,
    //private val baseFilters: Map<ProductFilterKey, Any>
) : PagingSource<ProductPagingKey, ProductThumbnail>() {

    // Define the sequence of stock statuses to fetch
    private val statusOrder = listOf("instock", "onbackorder", "outofstock")

    override fun getRefreshKey(state: PagingState<ProductPagingKey, ProductThumbnail>): ProductPagingKey? {
        // On refresh, always start from the beginning.
        return ProductPagingKey(status = statusOrder.first(), page = 1)
    }

    override suspend fun load(params: LoadParams<ProductPagingKey>): LoadResult<ProductPagingKey, ProductThumbnail> {
        // On the first load (refresh), params.key is null. Start with the first status.
        val currentKey = params.key ?: ProductPagingKey(status = statusOrder.first(), page = 1)

        return when (
            val result = wooProductsRemoteSource.getProductList(currentKey.page,
                queries + mapOf(
                    ProductFilterKey.STOCK_STATUS.apiKey to currentKey.status,
                    // You can define a consistent page size here
                    //FilterKey.PER_PAGE.apiKey to 10.toString()
                    FilterKey.PER_PAGE.apiKey to (params.loadSize / statusOrder.size).coerceAtLeast(10).toString()
                )
            )
        ) {
            is Result.Success -> {

                val nextKey: ProductPagingKey?

                if (result.data.isNotEmpty()) {
                    // If we got results, the next key is for the next page of the *current* status.
                    nextKey = currentKey.copy(page = currentKey.page + 1)
                    Log.d(TAG, "load: ${currentKey.page}")
                } else {
                    Log.d(TAG, "list is empty: ${currentKey.page}")
                    // If we got NO results, it's time to move to the *next* stock status.
                    val currentStatusIndex = statusOrder.indexOf(currentKey.status)
                    if (currentStatusIndex < statusOrder.lastIndex) {
                        // Move to the first page of the next status in our list.
                        val nextStatus = statusOrder[currentStatusIndex + 1]
                        Log.d(TAG, "load: $nextStatus")
                        nextKey = ProductPagingKey(status = nextStatus, page = 1)
                    } else {
                        Log.d(TAG, "load: last one")
                        // If we've exhausted the last status, there's nothing more to load.
                        nextKey = null
                    }
                }

                // If the current load was empty, but we have a nextKey (for the next status),
                // we should trigger an immediate load of that next key.
                if (result.data.isEmpty() && nextKey != null) {
                    Log.d(TAG, "load: No Data, loading next status ${nextKey.status}")
                    return load(
                        LoadParams.Refresh(
                            key = nextKey,
                            loadSize = params.loadSize,
                            placeholdersEnabled = false
                        )
                    )
                }

                LoadResult.Page(
                    data = result.data,
                    prevKey = null, // We only support appending.
                    nextKey = nextKey
                )

            }

            is Result.Failure -> {
                Log.e(TAG, "load: failed", result.error.toThrowable())
                LoadResult.Error(result.error.toThrowable())
            }

        }


    }
}

