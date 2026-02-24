package com.solutionium.shared.data.database.converter

import androidx.room.TypeConverter

class ListConverters {

    @TypeConverter
    fun stringToList(stringList: String): List<String> {
        return stringList.split(",")
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun intToList(list: String): List<Int> {
        return list.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun listToInt(list: List<Int>): String {
        return list.joinToString(",")
    }

}