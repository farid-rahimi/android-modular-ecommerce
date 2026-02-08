package com.solutionium.data.api.woo

import com.solutionium.data.model.AppConfig
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result

interface WooConfigRemoteSource {

    suspend fun getAppConfig(): Result<AppConfig, GeneralError>

    suspend fun getPrivacyPolicy(): Result<String, GeneralError>
}