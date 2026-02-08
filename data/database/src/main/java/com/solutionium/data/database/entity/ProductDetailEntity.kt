package com.solutionium.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solutionium.data.model.ProductVarType
import kotlinx.serialization.Serializable

@Entity(tableName = "product_detail")
data class ProductDetailEntity(

    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    val salesPrice: Double?,
    val regularPrice: Double?,
    val onSale: Boolean,
    val priceHtml: String,
    val varType: ProductVarType = ProductVarType.SIMPLE,
    val type: String,
    val manageStock: Boolean,
    val stockStatus: String,
    val stock: Int,
    val imageUrls: List<String>,
    val rating: Double,
    val ratingCount: Int,
    val shortDescription: String,
    val description: String,
    val category: String,
    val brand: String?,
    val attributes: List<ProductAttributeSerializable>,
    val defaultAttributes: List<VariationAttributeSerializable>,
    val variations: List<Int>?
)

@Serializable // This annotation is crucial
data class ProductAttributeSerializable(
    val id: Int,
    val name: String,
    val slug: String,
    val variation: Boolean,
    val options: List<String>
)

@Serializable // This annotation is crucial
data class VariationAttributeSerializable(
    val id: Int,
    val name: String,
    val slug: String? = null,
    val option: String
)