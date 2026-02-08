package com.solutionium.data.model

data class Address(
    val id: Int?,
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
    val isDefault: Boolean
)
