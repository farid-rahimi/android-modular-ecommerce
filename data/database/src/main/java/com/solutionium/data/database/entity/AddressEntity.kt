package com.solutionium.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userId: String,
    val title: String?,
    val firstName: String,
    val lastName: String,
    val company: String?,
    val address1: String,
    val address2: String?,
    val city: String,
    val state: String,
    val postcode: String,
    val country: String,
    val email: String?,
    val phone: String?,
    val isDefault: Boolean = false
)
