package com.solutionium.sharedui.common.component

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import com.solutionium.shared.ios.IosProductItem
import com.solutionium.shared.data.model.ProductCatType
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.ProductVarType
import com.solutionium.sharedui.designsystem.theme.WooTheme
import platform.UIKit.UIViewController

class IosProductListComposeHost(initialItems: List<IosProductItem>) {
    private var products by mutableStateOf(initialItems.map { it.toProductThumbnail() })

    private val controller = ComposeUIViewController(
        configure = {
            enforceStrictPlistSanityCheck = false
        }
    ) {
        WooTheme {
            ProductThumbnailList(products = products, onProductClick = {})
        }
    }

    fun viewController(): UIViewController = controller

    fun updateProducts(items: List<IosProductItem>) {
        products = items.map { it.toProductThumbnail() }
    }
}

private fun IosProductItem.toProductThumbnail(): ProductThumbnail = ProductThumbnail(
    id = id,
    name = name,
    price = price,
    imageUrl = imageUrl,
    rating = 0.0,
    ratingCount = 0,
    shortDescription = "",
    categories = emptyList(),
    brands = emptyList(),
    tags = emptyList(),
    onSale = onSale,
    salePrice = if (onSale) price else null,
    regularPrice = regularPrice,
    varType = ProductVarType.SIMPLE,
    type = ProductCatType.OTHER,
    manageStock = manageStock,
    stock = stockCount,
    stockStatus = stockStatus,
    decants = null,
    scentGroup = null,
    volume = null,
    sizingRange = null,
    availableColorsHex = null,
    hasSimpleAddToCart = true,
    shippingClass = null,
    appOffer = 0.0,
)
