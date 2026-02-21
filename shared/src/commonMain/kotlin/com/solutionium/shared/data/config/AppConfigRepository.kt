package com.solutionium.shared.data.config

import com.solutionium.shared.data.model.AppConfig
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result

interface AppConfigRepository {

    suspend fun getAppConfig(): Result<AppConfig, GeneralError>

    suspend fun getPrivacyPolicy(): Result<String, GeneralError>

}