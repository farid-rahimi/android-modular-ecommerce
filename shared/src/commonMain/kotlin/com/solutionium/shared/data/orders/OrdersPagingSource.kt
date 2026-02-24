package com.solutionium.shared.data.orders

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solutionium.shared.data.api.woo.WooOrderRemoteSource
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.toThrowable

// This PagingSource fetches pages of ProductThumbnail items from the WooProductsRemoteSource
internal class OrdersPagingSource(
    private val queries: Map<String, String>,
    private val wooOrderRemoteSource: WooOrderRemoteSource,
) : PagingSource<Int, Order>() {

    override fun getRefreshKey(state: PagingState<Int, Order>): Int =
        STARTING_PAGE_INDEX

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Order> = try {
        val page = params.key ?: STARTING_PAGE_INDEX
        when (val response = wooOrderRemoteSource.getOrderList(page = page, queries = queries)) {
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
        private const val PAGE_SIZE = 10
    }
}


