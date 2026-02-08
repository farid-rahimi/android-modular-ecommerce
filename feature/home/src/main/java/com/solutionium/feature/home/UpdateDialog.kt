package com.solutionium.feature.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri

// In a new file, e.g., feature/home/src/main/java/.../component/UpdateDialog.kt
@Composable
fun UpdateDialog(
    updateInfo: UpdateInfo,
    onDismiss: () -> Unit,
    onContactSupportClick: () -> Unit // Add this callback
) {
    val context = LocalContext.current
    val isForced = updateInfo.type == UpdateType.FORCED

    fun openPlayStore() {
        val packageName = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "market://details?id=$packageName".toUri()
                )
            )
        } catch (e: Exception) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=$packageName".toUri()
                )
            )
        }
    }

    AlertDialog(
        onDismissRequest = {
            if (!isForced) {
                onDismiss()
            }
        },
        title = { Text(stringResource(R.string.update_available)) },
        text = {
            val message = if (isForced) {
                stringResource(R.string.force_update_text)
            } else {
                stringResource(R.string.update_text, updateInfo.latestVersionName)
            }
            Text(message)
        },
        confirmButton = {
            Button(onClick = { openPlayStore() }) {
                Text(stringResource(R.string.update_now))
            }
        },
        dismissButton = {
            if (isForced) {
                TextButton(onClick = onContactSupportClick) {
                    Text(stringResource(R.string.contact_support))
                }

            } else {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.later))
                }
            }
        }
    )
}