package com.solutionium.woo.fcm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.solutionium.woo.MainActivity
import com.solutionium.woo.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FCM_Service"

    /**
     * Called when a new token for the app instance is generated.
     * This token is the unique address for this app instance.
     * You MUST send this token to your backend server to send targeted notifications.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
    }

    /**
     * Called when a message is received from FCM.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Handle notifications with a visible payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            showNotification(remoteMessage)
        }
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val channelId = "default_channel_id"
        val notification = remoteMessage.notification ?: return

        createNotificationChannel(channelId)
        // Create an Intent to launch MainActivity when the notification is tapped.
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Add custom data to handle navigation (e.g., open a specific order)
            remoteMessage.data["order_id"]?.let { orderId ->
                putExtra("order_id", orderId)
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // You need to add this icon
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // Set the action for when the user taps the notification
            .setAutoCancel(true) // Dismiss notification on tap

        // Check for permission before trying to show the notification (Android 13+)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.w(TAG, "POST_NOTIFICATIONS permission not granted. Cannot show notification.")
            // On a real app, you would have already requested this permission from the user.
            return
        }

        with(NotificationManagerCompat.from(this)) {
            val notificationId = System.currentTimeMillis().toInt()
            notify(notificationId, notificationBuilder.build())
        }
    }

    // CORRECTED: This is now a standard private function with the required API level check.
    private fun createNotificationChannel(channelId: String) {
        // NotificationChannel class is new and not in the support library, so it's only for API 26+.
        //Log.d(TAG, "Creating notification channel for Android Oreo and above.")
        val name = "Default Channel"
        val descriptionText = "Channel for general app notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}