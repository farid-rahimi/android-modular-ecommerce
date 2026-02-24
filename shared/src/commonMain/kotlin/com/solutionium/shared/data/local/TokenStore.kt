package com.solutionium.shared.data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import kotlinx.coroutines.flow.Flow
import com.russhwolf.settings.Settings
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow

interface TokenStore {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
    fun saveUserId(userId: String)
    fun getUserId(): String?
    fun saveSuperUser(isSuperUser: Boolean)
    fun isSuperUser(): Boolean
    fun observeToken(): Flow<String?>
    fun observeSuperUser(): Flow<Boolean>
}

class AuthTokenLocalDataSource(
    private val settings: Settings
) : TokenStore {

    private val observableSettings: ObservableSettings by lazy {
        settings as? ObservableSettings ?: throw IllegalStateException("Settings must be observable")
    }

    companion object {
        private const val KEY_AUTH_TOKEN = "user_auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_SUPER_USER = "super_user"
    }

    override fun saveToken(token: String) = settings.putString(KEY_AUTH_TOKEN, token)
    override fun getToken(): String? = settings.getStringOrNull(KEY_AUTH_TOKEN)

    override fun clearToken() {
        settings.remove(KEY_AUTH_TOKEN)
        settings.remove(KEY_USER_ID)
        settings.remove(KEY_SUPER_USER)
    }

    override fun saveUserId(userId: String) = settings.putString(KEY_USER_ID, userId)
    override fun getUserId(): String? = settings.getStringOrNull(KEY_USER_ID)

    override fun saveSuperUser(isSuperUser: Boolean) = settings.putBoolean(KEY_SUPER_USER, isSuperUser)
    override fun isSuperUser(): Boolean = settings.getBoolean(KEY_SUPER_USER, false)

    // Using the coroutine extension from the library
    @OptIn(ExperimentalSettingsApi::class)
    override fun observeToken(): Flow<String?> =
        observableSettings.getStringOrNullFlow(KEY_AUTH_TOKEN)

    @OptIn(ExperimentalSettingsApi::class)
    override fun observeSuperUser(): Flow<Boolean> =
        observableSettings.getBooleanFlow(KEY_SUPER_USER, false)
}