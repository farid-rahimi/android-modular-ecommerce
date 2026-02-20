package com.solutionium.shared.data.network.response


import com.solutionium.shared.data.network.common.MetaDatum
import kotlinx.serialization.*
import kotlinx.serialization.json.*

typealias WooCouponListResponse = List<WooCouponResponse>

@Serializable
data class WooCouponResponse (
    val id: Long,
    val code: String,
    val amount: String,

    @SerialName("date_created")
    val dateCreated: String,

    @SerialName("date_created_gmt")
    val dateCreatedGmt: String,

    @SerialName("date_modified")
    val dateModified: String,

    @SerialName("date_modified_gmt")
    val dateModifiedGmt: String,

    @SerialName("discount_type") // fixed_cart, percent, fixed_product
    val discountType: String,

    val description: String,

    @SerialName("date_expires")
    val dateExpires: String? = null,

    @SerialName("date_expires_gmt")
    val dateExpiresGmt: String? = null,

    @SerialName("usage_count")
    val usageCount: Int,

    @SerialName("individual_use")
    val individualUse: Boolean,

    @SerialName("product_ids")
    val productIDS: List<Int> = emptyList(),

    @SerialName("excluded_product_ids")
    val excludedProductIDS: List<Int> = emptyList(),

    @SerialName("usage_limit")
    val usageLimit: Int? = null,

    @SerialName("usage_limit_per_user")
    val usageLimitPerUser: Int? = null,

    @SerialName("limit_usage_to_x_items")
    val limitUsageToXItems: Int? = null,

    @SerialName("free_shipping")
    val freeShipping: Boolean,

    @SerialName("product_categories")
    val productCategories: List<Int> = emptyList(),

    @SerialName("excluded_product_categories")
    val excludedProductCategories: List<Int> = emptyList(),

    @SerialName("exclude_sale_items")
    val excludeSaleItems: Boolean,

    @SerialName("minimum_amount")
    val minimumAmount: String,

    @SerialName("maximum_amount")
    val maximumAmount: String,

    @SerialName("email_restrictions")
    val emailRestrictions: List<String>? = null,


    @SerialName("meta_data")
    val metaData: List<MetaDatum>? = null,

    )

val WooCouponResponse.includedBrandIds: List<Int>
    get() {

        val metaValue = this.metaData?.find { it.key == "product_brands" }?.value


        // 2. Use Json.decodeFromString to parse the string into a List<Int>.
        // A try-catch block is essential to handle malformed strings.
        return try {
            val stringValue = metaValue?.jsonArray ?: return emptyList()
            convertJsonArrayToList(stringValue)
        } catch (e: Exception) {
            // If parsing fails for any reason, return an empty list.
            emptyList()
        }

//        val metaValue = this.metaData?.find { it.key == "product_brands" }?.value
//        return metaValue?.let {
//            try {
//                Json.decodeFromString<List<Int>>(it)
//            } catch (e: Exception) {
//                emptyList()
//            }
//        } ?: emptyList()
    }

// Extension property to get the list of excluded brand IDs
val WooCouponResponse.excludedBrandIds: List<Int>
    get() {
        val metaValue = this.metaData?.find { it.key == "exclude_product_brands" }?.value


        // 2. Use Json.decodeFromString to parse the string into a List<Int>.
        // A try-catch block is essential to handle malformed strings.
        return try {
            val stringValue = metaValue?.jsonArray ?: return emptyList()
            convertJsonArrayToList(stringValue)
        } catch (e: Exception) {
            // If parsing fails for any reason, return an empty list.
            emptyList()
        }
//        val metaValue = this.metaData?.find { it.key == "exclude_product_brands" }?.value?.jsonPrimitive?.content
//        return metaValue?.let {
//            try {
//                Json.decodeFromString<List<Int>>(it)
//            } catch (e: Exception) {
//                emptyList()
//            }
//        } ?: emptyList()
    }

fun convertJsonArrayToList(element: JsonElement?): List<Int> {
    // 1. Safely cast the JsonElement to a JsonArray.
    val jsonArray = element as? JsonArray ?: return emptyList()

    // 2. Map each element of the array to an Int.
    // We use `jsonPrimitive` and `intOrNull` for safe conversion.
    // `mapNotNull` will automatically discard any null results.
    return jsonArray.mapNotNull { it.jsonPrimitive.intOrNull }
}

