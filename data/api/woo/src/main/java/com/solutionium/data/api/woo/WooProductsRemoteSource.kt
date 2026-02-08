package com.solutionium.data.api.woo

import com.solutionium.data.model.AttributeTerm
import com.solutionium.data.model.Brand
import com.solutionium.data.model.CartItemServer
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewReview
import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.ProductVariation
import com.solutionium.data.model.Result
import com.solutionium.data.model.Review
import com.solutionium.data.network.response.CartCheckError

interface WooProductsRemoteSource {

    suspend fun getProductDetails(productId: Int): Result<ProductDetail, GeneralError>
    suspend fun getProductDetails(slug: String): Result<ProductDetail, GeneralError>

    suspend fun getProductList(
        page: Int,
        queries: Map<String, String>
    ): Result<List<ProductThumbnail>, GeneralError>

    suspend fun getBrandList(
        queries: Map<String, String>
    ): Result<List<Brand>, GeneralError>

    suspend fun getCartUpdateServer(
        queries: Map<String, String>
    ): Result<List<CartItemServer>, GeneralError>

    suspend fun getAttributeTerms(
        attributeId: Int,
        queries: Map<String, String>
    ): Result<List<AttributeTerm>, GeneralError>
//
//    suspend fun getProductDetailsListById(
//        productIds: List<Int>
//    ): Result<List<ProductDetail>, GeneralError>

    suspend fun getProductVariations(productId: Int): Result<List<ProductVariation>, GeneralError>


    companion object {
        const val PAGE_SIZE = 20 // WOO API default page size
    }

    suspend fun getProductReviews(page: Int, queries: Map<String, String>): Result<List<Review>, GeneralError>
    suspend fun submitReview(review: NewReview): Result<Review, GeneralError>
}