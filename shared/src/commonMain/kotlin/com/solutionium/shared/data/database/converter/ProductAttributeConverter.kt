package com.solutionium.shared.data.database.converter

import androidx.room.TypeConverter
import com.solutionium.shared.data.database.entity.ProductAttributeSerializable
import com.solutionium.shared.data.database.entity.VariationAttributeSerializable
import kotlinx.serialization.json.Json


class ProductAttributeConverter {

    /**
     * Converts a JSON String to a List<ProductAttribute>.
     */
    @TypeConverter
    fun fromString(jsonString: String?): List<ProductAttributeSerializable>? {
        if (jsonString == null) return null
        return Json.decodeFromString<List<ProductAttributeSerializable>>(jsonString)
    }

    /**
     * Converts a List<ProductAttribute> to its JSON String representation.
     */
    @TypeConverter
    fun fromList(attributes: List<ProductAttributeSerializable>?): String? {
        if (attributes == null) return null
        return Json.encodeToString(attributes)
    }

    /**
     * Converts a JSON String to a List<ProductAttribute>.
     */
    @TypeConverter
    fun fromVarString(jsonString: String?): List<VariationAttributeSerializable>? {
        if (jsonString == null) return null
        return Json.decodeFromString<List<VariationAttributeSerializable>>(jsonString)
    }

    /**
     * Converts a List<ProductAttribute> to its JSON String representation.
     */
    @TypeConverter
    fun fromVarList(attributes: List<VariationAttributeSerializable>?): String? {
        if (attributes == null) return null
        return Json.encodeToString(attributes)
    }
}