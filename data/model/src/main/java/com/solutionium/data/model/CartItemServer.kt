package com.solutionium.data.model

data class CartItemServer(

    val id: Int,

    val price: Double,

    val regularPrice: Double,

    val manageStock: Boolean,

    val stockQuantity: Int,

    val stockStatus: String,

    val backOrder: String

)
