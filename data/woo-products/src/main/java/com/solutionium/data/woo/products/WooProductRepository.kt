package com.solutionium.data.woo.products

import androidx.paging.PagingData
import com.solutionium.data.model.AttributeTerm
import com.solutionium.data.model.AttributeTermsListType
import com.solutionium.data.model.Brand
import com.solutionium.data.model.BrandListType
import com.solutionium.data.model.CartItemServer
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewReview
import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.ProductVariation
import com.solutionium.data.model.Result
import com.solutionium.data.model.Review
import kotlinx.coroutines.flow.Flow

interface WooProductRepository {

    suspend fun getProductDetails(productId: Int): Result<ProductDetail, GeneralError>
    suspend fun getProductDetails(slug: String): Result<ProductDetail, GeneralError>


    suspend fun getProductsList(
        queries: Map<String, String>
    ): Result<List<ProductThumbnail>, GeneralError>

    fun getProductListStream(
        queries: Map<String, String>
    ): Flow<PagingData<ProductThumbnail>>

    suspend fun getReviewList(
        queries: Map<String, String>
    ): Result<List<Review>, GeneralError>

    fun getReviewListStream(
        queries: Map<String, String>
    ): Flow<PagingData<Review>>

    suspend fun getBrandList(type: BrandListType): Result<List<Brand>, GeneralError>

    suspend fun getAttributeTerms(listType: AttributeTermsListType): Result<List<AttributeTerm>, GeneralError>
    //suspend fun getProductDetailsForValidation(productIds: List<Int>): Result<List<ProductDetail>, GeneralError>

    suspend fun getCartUpdateServer(productIds: List<Int>): Result<List<CartItemServer>, GeneralError>
    suspend fun getProductVariations(productId: Int): Result<List<ProductVariation>, GeneralError>
    suspend fun submitReview(review: NewReview): Result<Review, GeneralError>
}