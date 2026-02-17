package com.solutionium.data.api.woo.impl

import com.solutionium.data.api.woo.WooConfigRemoteSource
import com.solutionium.data.api.woo.converters.toModel
import com.solutionium.data.api.woo.handleNetworkResponse
import com.solutionium.data.model.AppConfig
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.network.clients.UserClient
import javax.inject.Inject

internal class WooConfigRemoteSourceImpl @Inject constructor(
    private val userService: UserClient,
) : WooConfigRemoteSource {
    override suspend fun getAppConfig(): Result<AppConfig, GeneralError> =
        handleNetworkResponse(
            networkCall = { userService.getAppConfig() },
            mapper = { it.toModel() }
        )

    override suspend fun getPrivacyPolicy(): Result<String, GeneralError> =
        handleNetworkResponse(
            networkCall = { userService.getPrivacyPolicy() },
            mapper = { it.message ?: "" }
        )

}