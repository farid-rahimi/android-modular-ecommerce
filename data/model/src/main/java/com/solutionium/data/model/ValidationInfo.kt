package com.solutionium.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ValidationInfo(
    val type: ChangeType,
    val values: List<String> = emptyList() // e.g., ["newPrice", "oldPrice"] or ["productName", "newStock"]
)

@Serializable
enum class ChangeType {
    PRICE_CHANGED,
    REGULAR_PRICE_CHANGED,
    STOCK_CHANGED,
    MULTIPLE_ISSUES,
    OUT_OF_STOCK,
    NOT_AVAILABLE,
    NETWORK_ERROR // For when server validation fails
}

@Serializable
sealed class UiText {
    // For simple, static text that doesn't need translation
    @Serializable
    data class DynamicString(val value: String) : UiText()

    // For text that needs to be resolved from string resources, with optional arguments
    @Serializable
    data class StringResource(
        val resId: Int, // Use a String identifier, not an Int
        val args: List<String> = emptyList()
    ) : UiText()
}