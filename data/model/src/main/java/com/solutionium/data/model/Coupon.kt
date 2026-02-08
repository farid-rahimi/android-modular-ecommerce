package com.solutionium.data.model

data class Coupon(

    val id: Long,
    val code: String,
    val amount: Double,
    val discountType: String, // fixed_cart, percent, fixed_product
    val description: String,

    val dateExpires: String? = null,

    val individualUse: Boolean,

    val usageCount: Int = 0,
    val usageLimit: Int? = null,
    val usageLimitPerUser: Int? = null,
    val limitUsageToXItems: Int? = null,

    val freeShipping: Boolean,

    val productIDS: List<Int> = emptyList(),
    val excludedProductIDS: List<Int> = emptyList(),

    val productCategories: List<Int> = emptyList(),
    val excludedProductCategories: List<Int> = emptyList(),

    val brandIDS: List<Int> = emptyList(),
    val excludedBrandIDS: List<Int> = emptyList(),

    val emailRestrictions: List<String> = emptyList(),

    val minimumAmount: Double,
    val maximumAmount: Double,

    val excludeSaleItems: Boolean,


)