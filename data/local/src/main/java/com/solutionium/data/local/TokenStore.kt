// --- In: com.solutionium.data.local.AuthTokenLocalDataSource.kt ---
package com.solutionium.data.local // Or your preferred package

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject

// Interface for easier testing and abstraction (optional but good practice)
interface TokenStore {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
    fun saveUserId(userId: String)
    fun getUserId(): String?
    fun saveSuperUser(isSuperUser: Boolean)
    fun isSuperUser(): Boolean
    fun observeToken(): Flow<String?>
    fun observeSuperUser(): Flow<Boolean?>
}

class AuthTokenLocalDataSource @Inject constructor(
    private val appContext: Context
) : TokenStore {

    companion object {
        private const val MASTER_KEY_ALIAS = "_androidx_security_master_key_" // Default alias or your custom one
        private const val KEY_AUTH_TOKEN = "user_auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_SUPER_USER = "super_user"
        private const val LANGUAGE = "app_language"
        private const val PREFS_FILENAME = "auth_woo_qein_store"
    }

    private val masterKey: MasterKey by lazy {
        // Build the KeyGenParameterSpec for the master key
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256) // AES-256
            .build()

        // Build the MasterKey using the KeyGenParameterSpec
        // This will create the key in the Android Keystore if it doesn't exist,
        // or retrieve it if it does.
        MasterKey.Builder(appContext, MASTER_KEY_ALIAS)
            .setKeyGenParameterSpec(keyGenParameterSpec)
            .build()
    }

//    private val sharedPreferences: SharedPreferences by lazy {
//        EncryptedSharedPreferences.create(
//            appContext,
//            "auth_prefs_woo_store", // A unique filename for your app's encrypted preferences
//            masterKey, // <-- USE THE MasterKey OBJECT HERE
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }

    private val sharedPreferences: SharedPreferences by lazy {
        try {
            createEncryptedSharedPreferences()
        } catch (e: GeneralSecurityException) {
            // This can happen if the master key is invalid, e.g., after app reinstall
            // or clearing app data.
            handleEncryptionError(e)
            // Retry creating the EncryptedSharedPreferences after clearing old data.
            createEncryptedSharedPreferences()
        } catch (e: IOException) {
            // A different kind of file-related error.
            handleEncryptionError(e)
            createEncryptedSharedPreferences()
        }
    }

    private fun createEncryptedSharedPreferences(): SharedPreferences {

        return EncryptedSharedPreferences.create(
            appContext,
            PREFS_FILENAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun handleEncryptionError(e: Exception) {
        // Log the error for debugging purposes. Use your app's logging framework.
        // e.g., FirebaseCrashlytics.recordException(e)
        //android.util.Log.e("TokenStore", "Failed to decrypt SharedPreferences, deleting and recreating.", e)

        // The old data is unrecoverable. Delete the corrupted SharedPreferences file.
        appContext.deleteSharedPreferences(PREFS_FILENAME)
    }

//    companion object {
//        private const val KEY_AUTH_TOKEN = "user_auth_token"
//    }

    override fun saveToken(token: String) {
        sharedPreferences.edit {
            putString(KEY_AUTH_TOKEN, token)
        }
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    override fun clearToken() {
        sharedPreferences.edit {
            remove(KEY_AUTH_TOKEN)
            remove(KEY_USER_ID)
            remove(KEY_SUPER_USER)
        }
    }

    override fun saveUserId(userId: String) {
        sharedPreferences.edit {
            putString(KEY_USER_ID, userId)
        }
    }



    override fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    override fun saveSuperUser(isSuperUser: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_SUPER_USER, isSuperUser)
        }
    }

    override fun isSuperUser(): Boolean {
        return sharedPreferences.getBoolean(KEY_SUPER_USER, false)
    }

    override fun observeToken(): Flow<String?> = callbackFlow {
        // This listener will be triggered whenever a value in SharedPreferences changes.
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == KEY_AUTH_TOKEN) {
                // When the auth token changes, send the new value into the flow.
                trySend(getToken())
            }
        }

        // 1. Immediately send the current token value when the flow is first collected.
        trySend(getToken())

        // 2. Register the listener to observe future changes.
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        // 3. When the flow is cancelled, unregister the listener to avoid memory leaks.
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.conflate()

    override fun observeSuperUser(): Flow<Boolean?> = callbackFlow {
        // This listener will be triggered whenever a value in SharedPreferences changes.
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == KEY_SUPER_USER) {
                // When the auth token changes, send the new value into the flow.
                trySend(isSuperUser())
            }
        }

        // 1. Immediately send the current token value when the flow is first collected.
        trySend(isSuperUser())

        // 2. Register the listener to observe future changes.
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        // 3. When the flow is cancelled, unregister the listener to avoid memory leaks.
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.conflate()



}