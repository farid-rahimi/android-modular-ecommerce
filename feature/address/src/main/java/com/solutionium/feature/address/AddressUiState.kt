package com.solutionium.feature.address

import com.solutionium.data.model.Address


// Field names for error mapping
object AddressField {
    const val TITLE = "title"
    const val FIRST_NAME = "firstName"
    const val LAST_NAME = "lastName"
    const val STATE = "state"
    const val CITY = "city"
    const val ADDRESS_LINE_1 = "addressLine1"
    const val POSTAL_CODE = "postalCode"
    const val PHONE_NUMBER = "phoneNumber"
}

// Data class to hold the UI state for the form
data class AddressUiState(
    val addressId: Int? = null, // Null if adding new
    val title: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val state: String = "", // Could be a dropdown in a real app
    val city: String = "",
    val addressLine1: String = "",
    val addressLine2: String? = null,
    val postalCode: String = "",
    val phoneNumber: String? = "",
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessages: Map<String, Int> = emptyMap(), // For field-specific errors
    val generalError: String? = null
)

data class AddressListUiState(
    val addresses: List<Address> = emptyList(),
    val isLoading: Boolean = false,
    val generalError: String? = null,
    val showDeleteConfirmationDialog: Boolean = false,
    val addressToDelete: Address? = null

)