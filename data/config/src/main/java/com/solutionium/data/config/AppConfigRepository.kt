package com.solutionium.data.config

import com.solutionium.data.model.AppConfig
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result

interface AppConfigRepository {

    suspend fun getAppConfig(): Result<AppConfig, GeneralError>

    suspend fun getPrivacyPolicy(): Result<String, GeneralError>

}