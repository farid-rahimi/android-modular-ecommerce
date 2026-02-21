package com.solutionium.shared.data.config.impl

import com.solutionium.shared.data.api.woo.WooConfigRemoteSource
import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.AppConfig
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import kotlin.time.Clock

import kotlin.time.Duration.Companion.minutes // Add this
import kotlin.time.ExperimentalTime


internal class AppConfigRepositoryImpl(
    private val appConfigDataSource: WooConfigRemoteSource
) : AppConfigRepository {

    // 1. In-memory cache for the AppConfig
    private var cachedConfig: AppConfig? = null

    // 2. Timestamp to track when the cache was last updated
    private var lastFetchTimestamp: Long = 0

    // 3. Cache duration constant (1 hour in milliseconds)
    private val cacheDuration = 1.minutes.inWholeMilliseconds

    @OptIn(ExperimentalTime::class)
    override suspend fun getAppConfig(): Result<AppConfig, GeneralError> {

        val currentTime = Clock.System.now().toEpochMilliseconds() // Get the current time in milliseconds.

        // Check if cache is valid

        if (cachedConfig != null && (currentTime - lastFetchTimestamp) < cacheDuration) {
            // If cache is valid, return the cached data wrapped in a Success result
            return Result.Success(cachedConfig!!)
        }

        // If cache is invalid or expired, fetch from the data source
        val result = appConfigDataSource.getAppConfig()

        // If the fetch was successful, update the cache
        if (result is Result.Success) {
            cachedConfig = result.data
            lastFetchTimestamp = currentTime
        }

        return result
    }

    override suspend fun getPrivacyPolicy(): Result<String, GeneralError> =
        appConfigDataSource.getPrivacyPolicy()

}