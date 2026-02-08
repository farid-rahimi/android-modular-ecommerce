package com.solutionium.data.model

import kotlinx.serialization.Serializable
import java.util.regex.Pattern

data class ShippingMethod(
    val id: Int,
    val title: String,
    val methodId: String,
    val description: String,
    val cost: String,
    val settings: Map<String, MethodSetting>? = null,
) {
    fun calculateShippingCost(cartTotal: Double): Double {
        // Regex to find the [fee ...] shortcode and its parameters
        if (cost.isBlank()) return 0.0

        val feePattern = Pattern.compile("""\[fee\s+percent="([^"]+)"\s+min_fee="([^"]+)"\s+max_fee="([^"]+)"]""")
        val feeMatcher = feePattern.matcher(cost)

        var baseCost = 0.0
        var percentageFee = 0.0

        // Extract the base cost (the part before the shortcode)
        val feeShortcodePart = if (feeMatcher.find()) {
            feeMatcher.group(0)
        } else {
            null
        }

        val baseCostString = if (feeShortcodePart != null) {
            cost.substringBefore(feeShortcodePart).trim()
        } else {
            cost.trim() // If no shortcode, the whole string is the base cost
        }

        // --- CORRECTED PART ---
        // Clean up the base cost string by removing any trailing operator like '+' before parsing.
        val cleanedBaseCostString = baseCostString.replace(Regex("""\s*\+\s*$"""), "")
        baseCost = cleanedBaseCostString.toDoubleOrNull() ?: 0.0
        // --- END OF CORRECTION ---

        // If a fee shortcode was found, calculate the percentage-based fee
        if (feeShortcodePart != null) {
            try {
                val percent = feeMatcher.group(1)?.toDouble() ?: 0.0
                val minFee = feeMatcher.group(2)?.toDouble() ?: 0.0
                val maxFee = feeMatcher.group(3)?.toDouble() ?: 0.0

                // Calculate the fee: (cartTotal * percent / 100)
                var calculatedFee = cartTotal * percent / 100.0

                // Apply min and max fee constraints
                if (maxFee > 0.0 && calculatedFee > maxFee) {
                    calculatedFee = maxFee
                }
                if (calculatedFee < minFee) {
                    calculatedFee = minFee
                }
                percentageFee = calculatedFee
            } catch (e: Exception) {
                // In case of any parsing error with the fee attributes, default to zero
                percentageFee = 0.0
                println("Error parsing shipping fee shortcode: ${e.message}")
            }
        }

        // Total shipping cost is the base cost plus the calculated percentage fee
        return baseCost + percentageFee
    }

    fun isFreeShippingByCoupon(): Boolean {
        return methodId == "free_shipping" && settings?.get("requires")?.value == "coupon"
    }

    fun isFreeShippingByMinOrder():Boolean {
        return methodId == "free_shipping" && settings?.get("requires")?.value == "min_amount"
    }

    fun isEligibleForMinOrderAmount(subTotal: Double): Boolean {
        return subTotal >= (settings?.get("min_amount")?.value?.toDouble() ?: 0.0)
    }
}


data class MethodSetting (
    val id: String? = null,
    val label: String? = null,
    val description: String? = null,
    val type: String? = null,
    val value: String? = null,
    val default: String? = null,
    val tip: String? = null,
    val placeholder: String? = null,
)
