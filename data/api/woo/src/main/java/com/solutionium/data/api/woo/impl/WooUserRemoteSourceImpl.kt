package com.solutionium.data.api.woo.impl

import com.solutionium.data.api.woo.WooUserRemoteSource
import com.solutionium.data.api.woo.converters.toEditUserRequest
import com.solutionium.data.api.woo.converters.toUserAccess
import com.solutionium.data.api.woo.converters.toUserDetails
import com.solutionium.data.api.woo.converters.toUserWallet
import com.solutionium.data.api.woo.converters.toWalletConfig
import com.solutionium.data.api.woo.handleNetworkResponse
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.UserAccess
import com.solutionium.data.model.UserDetails
import com.solutionium.data.model.UserWallet
import com.solutionium.data.model.WalletConfig
import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.clients.DigitsClient
import com.solutionium.data.network.clients.UserClient

internal class WooUserRemoteSourceImpl(
    private val authService: DigitsClient,
    private val userService: UserClient
) : WooUserRemoteSource {
    override suspend fun sendOtp(phoneNumber: String): Result<Unit, GeneralError> =
        when (val result = authService.sendOTP(
            mapOf(
                "mobileNo" to phoneNumber,
                "countrycode" to "+98",
                "type" to "login"
            )
        )) {
            is NetworkResponse.Success -> {
                val digitsResponse = result.body
                if (digitsResponse?.code == 1) {
                    Result.Success(Unit)
                } else {
                    Result.Failure(GeneralError.UnknownError(Throwable(digitsResponse?.code.toString())))
                }
            }

            is NetworkResponse.ApiError -> {
                val errorResponse = result.body
                Result.Failure(
                    GeneralError.ApiError(
                        errorResponse.message,
                        errorResponse.code.toString(),
                        400
                    )
                )
            }

            is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
            is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
        }

    override suspend fun loginOrRegister(
        phoneNumber: String,
        otp: String
    ): Result<UserAccess, GeneralError> =
        when (val result = authService.oneClick(
            mapOf(
                "mobileNo" to phoneNumber,
                "countrycode" to "+98",
                "otp" to otp,
            )
        )) {
            is NetworkResponse.Success -> {
                val digitsResponse = result.body
                if (digitsResponse?.success == true) {
                    Result.Success(digitsResponse.data.toUserAccess())
                } else {
                    Result.Failure(GeneralError.UnknownError(Throwable(digitsResponse?.data.toString())))
                }
            }

            is NetworkResponse.ApiError -> {
                val errorResponse = result.body
                Result.Failure(
                    GeneralError.ApiError(
                        errorResponse.data?.msg,
                        errorResponse.data?.code,
                        400
                    )
                )
            }

            is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
            is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
        }

    override suspend fun loginUserPass(
        user: String,
        pass: String
    ): Result<UserAccess, GeneralError> =
        when (val result = authService.loginUser(
            //user = user.toRequestBody(MultipartBody.FORM), password = pass.toRequestBody(MultipartBody.FORM)
            user = user,
            password = pass
        )
        ) {
            is NetworkResponse.Success -> {
                val digitsResponse = result.body
                if (digitsResponse?.success == true) {
                    Result.Success(digitsResponse.data.toUserAccess())
                } else {
                    Result.Failure(GeneralError.UnknownError(Throwable(digitsResponse?.data.toString())))
                }
            }

            is NetworkResponse.ApiError -> {
                val errorResponse = result.body
                Result.Failure(
                    GeneralError.ApiError(
                        errorResponse.data?.msg,
                        errorResponse.data?.code,
                        400
                    )
                )
            }

            is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
            is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
        }

    override suspend fun logout(token: String?): Result<Boolean, GeneralError> =
        when (val result = authService.logout("Bearer $token")) {
            is NetworkResponse.Success -> {
                val response = result.body
                if (response?.success == true) {
                    Result.Success(true)
                } else {
                    Result.Failure(GeneralError.UnknownError(Throwable("Logout failed")))
                }
            }
            is NetworkResponse.ApiError -> {
                Result.Failure(GeneralError.UnknownError(Throwable("Logout failed")))
            }
            is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
            is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))

        }

    override suspend fun getMe(token: String?): Result<UserDetails, GeneralError> =
        handleNetworkResponse(
            networkCall = { userService.getMe("Bearer $token") },
            mapper = { response -> response.toUserDetails() }
        )

    override suspend fun updateUserProfile(
        token: String?,
        userId: String,
        userDetails: UserDetails
    ): Result<UserDetails, GeneralError> =
        handleNetworkResponse(
            networkCall = {
                userService.updateUser(
                    userId = userId,
                    userData = userDetails.toEditUserRequest(),
                    token = "Bearer $token"
                    )
            },
            mapper = {response -> response.toUserDetails()}
        )



    override suspend fun getUserWallet(token: String?): Result<UserWallet, GeneralError> =
        handleNetworkResponse(
            networkCall = { userService.getUserWallet("Bearer $token") },
            mapper = { response -> response.toUserWallet() }
        )


    override suspend fun getWalletConfig(): Result<WalletConfig, GeneralError> =
        handleNetworkResponse(
            networkCall = { userService.getWalletConfig() },
            mapper = { response -> response.toWalletConfig() }
        )


}