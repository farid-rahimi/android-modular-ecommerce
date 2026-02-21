package com.solutionium.data.database.converter

import androidx.room.TypeConverter
import com.solutionium.shared.data.model.ChangeType
import com.solutionium.shared.data.model.ValidationInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UiTextConverter {


    @TypeConverter
    fun fromString(jsonString: String?): ValidationInfo? {
        if (jsonString == null) return null
        return Json.decodeFromString<ValidationInfo>(jsonString)
    }

    @TypeConverter
    fun fromList(uiText: ValidationInfo?): String? {
        if (uiText == null) return null
        return Json.encodeToString(uiText)
    }

//    @TypeConverter
//    fun fromCString(jsonString: String?): ValidationInfo? {
//        if (jsonString == null) return null
//        return Json.decodeFromString<ValidationInfo>(jsonString)
//    }
//
//    @TypeConverter
//    fun fromMap(map: ValidationInfo?): String? {
//        if (map == null) return null
//        return Json.encodeToString(map)
//    }

}