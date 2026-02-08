package com.solutionium.data.config.impl

import com.solutionium.data.api.woo.WooConfigRemoteSource
import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.AppConfig
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AppConfigRepositoryImpl @Inject constructor(
    private val appConfigDataSource: WooConfigRemoteSource
) : AppConfigRepository {

    // 1. In-memory cache for the AppConfig
    private var cachedConfig: AppConfig? = null

    // 2. Timestamp to track when the cache was last updated
    private var lastFetchTimestamp: Long = 0

    // 3. Cache duration constant (1 hour in milliseconds)
    private val cacheDuration = TimeUnit.MINUTES.toMillis(1)

    override suspend fun getAppConfig(): Result<AppConfig, GeneralError> {

        if (cachedConfig != null && (System.currentTimeMillis() - lastFetchTimestamp) < cacheDuration) {
            // If cache is valid, return the cached data wrapped in a Success result
            return Result.Success(cachedConfig!!)
        }

        // If cache is invalid or expired, fetch from the data source
        val result = appConfigDataSource.getAppConfig()

        // If the fetch was successful, update the cache
        if (result is Result.Success) {
            cachedConfig = result.data
            lastFetchTimestamp = System.currentTimeMillis()
        }

        return result
    }

    override suspend fun getPrivacyPolicy(): Result<String, GeneralError> =
        appConfigDataSource.getPrivacyPolicy()

}