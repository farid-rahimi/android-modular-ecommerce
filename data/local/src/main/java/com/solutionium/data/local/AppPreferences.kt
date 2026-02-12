package com.solutionium.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject
import androidx.core.content.edit

// In your core/data module
interface AppPreferences {
    fun language(): Flow<String?>
    fun getLanguage(): String?
    suspend fun setLanguage(languageCode: String)

    // --- ADD THESE NEW METHODS ---
    fun getFcmToken(): String?
    suspend fun setFcmToken(token: String)
}

// In the implementation of the repository
class AppPreferencesImpl @Inject constructor(
    private val appContext: Context
) : AppPreferences {

    companion object {
        private const val LANGUAGE = "app_language"
        private const val FCM_TOKEN = "fcm_token"
    }

    private val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(
        "app_prefs", Context.MODE_PRIVATE
    )

    override fun language(): Flow<String?> = callbackFlow {
        // This listener will be triggered whenever a value in SharedPreferences changes.
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == LANGUAGE) {
                // When the auth token changes, send the new value into the flow.

                trySend(getLanguage())
            }
        }

        // 1. Immediately send the current token value when the flow is first collected.
        trySend(getLanguage())

        // 2. Register the listener to observe future changes.
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        // 3. When the flow is cancelled, unregister the listener to avoid memory leaks.
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.conflate()

    override fun getLanguage(): String? {
        return sharedPreferences.getString(LANGUAGE, null)
    }

    override suspend fun setLanguage(languageCode: String) {
        sharedPreferences.edit { putString(LANGUAGE, languageCode) }
    }

    // --- IMPLEMENT THE NEW METHODS ---
    override fun getFcmToken(): String? {
        return sharedPreferences.getString(FCM_TOKEN, null)
    }

    override suspend fun setFcmToken(token: String) {
        sharedPreferences.edit { putString(FCM_TOKEN, token) }
    }

}