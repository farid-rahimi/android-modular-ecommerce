package com.solutionium.shared.data.model

data class FilterCriterion(
    val key: String,
    val value: Any, // Use 'Any' for flexibility, but be mindful of type safety when converting to query
)


// Helper function to convert a list of FilterCriterion to a query parameter map
fun List<FilterCriterion>.toQueryMap(): Map<String, String> {
    val queryMap = mutableMapOf<String, String>()
    this.forEach { criterion ->
        when (criterion.value) {
            is List<*> -> {
                // Handle list values, e.g., "key=value1,value2,value3" or multiple "key=value1&key=value2"
                // This depends heavily on your API's list format.
                // Example for comma-separated:
                queryMap[criterion.key] = criterion.value.joinToString(",")
            }
            else -> {
                // For simple values
                queryMap[criterion.key] = criterion.value.toString()
            }
        }
        // If criterion.operation is used, you might adjust the key or value format here
        // e.g., if operation is GREATER_THAN for 'price': "price[gt]=100"
        // This part becomes highly API-specific.
    }
    return queryMap
}


enum class FilterKey (val apiKey: String) {
    PAGE("page"),
    PER_PAGE("per_page"),
    ORDER_BY("orderby"), // Example values: date, id, include, title, slug, price, popularity, rating
    ORDER("order"), // Example values: asc, desc
}

enum class ProductFilterKey(val apiKey: String) {
    BRAND_ID("brand"),
    CATEGORY_ID("category"),
    MIN_PRICE("min_price"),
    MAX_PRICE("max_price"),
    ATTRIBUTE("attribute"), // Example of a namespaced attribute key
    ATTRIBUTE_TERM("attribute_term"),
    INCLUDE_IDS("include"),
    ON_SALE("on_sale"),
    STOCK_STATUS("stock_status"), // Example values: instock, outofstock, onbackorder
    TAG("tag"),
    SEARCH("search"),
    FEATURED("featured"),
    // ... add more common keys
}

enum class OrderFilterKey(val apiKey: String) {
    USER_ID("customer"),
    STATUS("status")
}