package com.solutionium.data.model

data class UserDetails(
    var displayName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val nickName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val isSuperUser: Boolean = false,
    var fcmToken: String = ""
)

data class UserAddress(
    val id: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val isDefault: Boolean = false
)

data class OrderSummary(
    val orderId: String,
    val date: String,
    val totalAmount: String,
    val status: String // e.g., "Processing", "Shipped", "Delivered"
)

data class UserAccess(
    val userId: String,
    val token: String,
    val tokenType: String,
    val action: ActionType = ActionType.LOGIN
)

enum class ActionType {
    LOGIN,
    REGISTER
}


