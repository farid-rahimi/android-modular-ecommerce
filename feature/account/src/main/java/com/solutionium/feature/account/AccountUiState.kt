package com.solutionium.feature.account

import com.solutionium.data.model.ContactInfo
import com.solutionium.data.model.Order
import com.solutionium.data.model.UserDetails
import com.solutionium.data.model.UserWallet

//sealed interface AccountUiState {
//
//
//    data object Loading : AccountUiState
//    data object LoggedOut : AccountUiState // Initial state, show phone input
//    data class OtpVerification(val phoneNumber: String) : AccountUiState
//    data class NewUserDetailsInput(val phoneNumber: String, val otpVerifiedToken: String) : AccountUiState // For new users
//    data class LoggedIn(
//        val userDetails: UserDetails? = null,
//        val userWallet: UserWallet? = null,
//        val isLoadingWallet: Boolean = false,
//        val latestOrder: Order? = null
//    ) : AccountUiState
//    data class ViewWalletTransactions(
//        val userDetails: UserDetails,
//        val userWallet: UserWallet? = null,
//    ) : AccountUiState
//    // --- New states for sub-sections ---
//    data class EditProfile(val userDetails: UserDetails) : AccountUiState
//
//    data class Error(val message: String) : AccountUiState
//
//}

data class AccountUIState(
    val lastStage: AccountStage? = null,

    val stage: AccountStage = AccountStage.LoggedOut,

    val isLoading: Boolean = false,
    val isRequestingOtp: Boolean = false,
    val isVerifyingOtp: Boolean = false,
    val isSubmittingUserDetails: Boolean = false,
    val isLoggingOut: Boolean = false,
    val phoneNumber: String? = null,
    val email: String? = null,
    val name: String? = null,
    val otp: String? = null,
    val username: String = "",
    val password: String = "",
    val userDetails: UserDetails? = null,
    val userWallet: UserWallet? = null,
    val isLoadingWallet: Boolean = false,
    val latestOrder: Order? = null,
    val isLoadingLatestOrder: Boolean = false,
    val message: String? = null,
    val validationErrors: FieldErrors = FieldErrors(),
    val showLogoutConfirmDialog: Boolean = false, // Add this new state
    val currentLanguage: String = "none",
    val privacyPolicy: String = "",

    val contactInfo: ContactInfo? = null,
    val showContactSupportDialog: Boolean = false
)

enum class AccountStage{
    Error,
    EditProfile,
    ViewWalletTransactions,
    LoggedIn,
    NewUserDetailsInput,
    OtpVerification,
    ChangeLanguage,
    LoggedOut
}

data class FieldErrors(
    val firstNameError: Int? = null,
    val lastNameError: Int? = null,
    val emailError: Int? = null
)

