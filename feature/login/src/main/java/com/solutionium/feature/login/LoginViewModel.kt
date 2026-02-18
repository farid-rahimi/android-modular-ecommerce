package com.solutionium.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionium.data.model.ActionType
import com.solutionium.data.model.Result
import com.solutionium.data.model.UserDetails
import com.solutionium.domain.user.CheckLoginUserUseCase
import com.solutionium.domain.user.EditUserDetailsUseCase
import com.solutionium.domain.user.GetCurrentUserUseCase
import com.solutionium.domain.user.LoginOrRegisterUseCase
import com.solutionium.domain.user.SendOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// This enum determines which screen to show: phone number input or OTP verification
enum class LoginStep {
    PHONE_INPUT,
    OTP_VERIFICATION,
    USER_DETAILS // New step for registration/editing
}
// This state holds all the necessary data for the entire login flow
data class LoginUiState(
    val step: LoginStep = LoginStep.PHONE_INPUT,
    val phoneNumber: String = "",
    val otp: String = "",
    // User details fields
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val isNewUser: Boolean = false, // To know if we should show details screen after OTP
    val isLoading: Boolean = false,
    val error: String? = null,
    val loginError: String? = null // Specific error for final login step
)


class LoginViewModel(
    private val requestOtpUseCase: SendOtpUseCase,
    private val loginOrRegisterUseCase: LoginOrRegisterUseCase,
    private val checkLoginUserUseCase: CheckLoginUserUseCase,
    private val editUserDetailsUseCase: EditUserDetailsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private fun checkLoginStatus() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val isLoggedIn = checkLoginUserUseCase() // Assume not logged in initially
//            if (isLoggedIn) {
//                //_state.update { it.copy(stage = AccountStage.LoggedIn, isLoading = false) }
//                loginCompleted()
//            } else {
//                _state.update { it.copy(step = LoginStep.PHONE_INPUT, isLoading = false) }
//            }
        }
    }

    fun onPhoneNumberChange(newNumber: String) {
        _state.update { it.copy(phoneNumber = newNumber, error = null) }
        //_phoneNumber.value = newNumber.filter { it.isDigit() }.take(11) // Basic validation
    }

    fun onOtpChange(newOtp: String) {
        _state.update { it.copy(otp = newOtp, error = null) }
        //_otp.value = newOtp.filter { it.isDigit() }.take(4) // Basic validation
    }


    // Called when the "Continue" button is clicked on the phone input screen
    fun requestOtp() {
        val phoneNumber = _state.value.phoneNumber ?: "0"
        if (phoneNumber.length < 11) {
            _state.update { it.copy(error = "Please enter a valid 11-digit phone number.") }

            return
        }

        viewModelScope.launch {
            //_screenState.value = AccountUiState.Loading
            _state.update { it.copy(otp = "", isLoading = true) }

            requestOtpUseCase(phoneNumber).collect { result ->

                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                step = LoginStep.OTP_VERIFICATION,
                                isLoading = false,
                                error = null
                            )
                        }
                        //_screenState.value = AccountUiState.OtpVerification(_phoneNumber.value)
                    }

                    is Result.Failure -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = "Failed to send OTP. Please try again."
                            )
                        }

                    }
                }

            }
        }
    }


    fun verifyOtp() {
        val otp = _state.value.otp ?: ""
        if (otp.length < 4) {
            //val currentState = _screenState.value
            _state.update { it.copy(error = "Please enter a valid 4-digit OTP.") }

            return
        }
        viewModelScope.launch {
            val phoneNumber = _state.value.phoneNumber ?: "0"
            _state.update { it.copy(isLoading = true) }
            //_screenState.value = AccountUiState.Loading
            //delay(500) // Simulate API call to verify OTP

            loginOrRegisterUseCase(phoneNumber, otp).collect { result ->

                when (result) {
                    is Result.Success -> {
                        val isRegister = result.data == ActionType.REGISTER
                        if (isRegister)
                            _state.update {
                                it.copy(
                                    step = LoginStep.USER_DETAILS,
                                    isLoading = false
                                )
                            }
                        else {

                            _state.update {
                                it.copy(
                                    otp = "",
                                    isLoading = false
                                )
                            }
                            loginCompleted()

                        }
                    }

                    is Result.Failure -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = "Otp Verification Failed"
                            )
                        }

                    }
                }

            }

        }
    }

    fun submitNewUserDetails(userDetails: UserDetails) {

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = editUserDetailsUseCase(userDetails)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    loginCompleted()
                }

                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false, error = result.error.toString()) }
                }
            }

        }
    }

    private fun loginCompleted() {

    }

}

