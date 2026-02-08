package com.solutionium.data.api.woo

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.UserAccess
import com.solutionium.data.model.UserDetails
import com.solutionium.data.model.UserWallet
import com.solutionium.data.model.WalletConfig

interface WooUserRemoteSource {

    suspend fun sendOtp(phoneNumber: String): Result<Unit, GeneralError>

    suspend fun loginOrRegister(phoneNumber: String, otp: String): Result<UserAccess, GeneralError>

    suspend fun logout(token: String?): Result<Boolean, GeneralError>
    suspend fun getMe(token: String?): Result<UserDetails, GeneralError>
    suspend fun updateUserProfile(token: String?, userId: String, userDetails: UserDetails): Result<UserDetails, GeneralError>

    suspend fun getUserWallet(token: String?): Result<UserWallet, GeneralError>
    suspend fun getWalletConfig(): Result<WalletConfig, GeneralError>
    suspend fun loginUserPass(user: String, pass: String): Result<UserAccess, GeneralError>
}