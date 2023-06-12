package com.alvarengadev.marketplacelist.utils.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import com.alvarengadev.marketplacelist.view.activity.MainActivity
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.utils.constants.Constants.Companion.CHANNEL_NOTIFICATION_FIREBASE
import com.alvarengadev.marketplacelist.utils.constants.Constants.Companion.NOTIFICATION_NAME_FIREBASE
import com.alvarengadev.marketplacelist.utils.notification.builder.PushNotificationApp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            generateNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }
    }

    private fun generateNotification(title: String?, message: String?) {

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        PushNotificationApp.Builder(this)
            .setSmallIcon(R.drawable.ic_shopping_cart)
            .setTitle(title)
            .setBodyMessage(message)
            .setNotificationName(NOTIFICATION_NAME_FIREBASE)
            .setChannel(CHANNEL_NOTIFICATION_FIREBASE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .create()
    }
}
