package com.alvarengadev.marketplacelist.utils.notification.builder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.alvarengadev.marketplacelist.view.activity.MainActivity
import java.util.Random

class PushNotificationApp {
    class Builder(private val context: Context) {

        private val notificationManager by lazy {
            context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        }

        private val notificationId = Random().nextInt()
        private var channel: String = ""
        private var notificationName: String = ""
        private var icon: Int = 0
        private var title: String? = ""
        private var bodyMessage: String? = ""
        private var contentIntent: PendingIntent? = null
        private var isAutoCancel: Boolean? = null

        fun setChannel(channel: String): Builder {
            this.channel = channel
            return this
        }

        fun setNotificationName(notificationName: String): Builder {
            this.notificationName = notificationName
            return this
        }

        fun setSmallIcon(icon: Int): Builder {
            this.icon = icon
            return this
        }

        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setBodyMessage(bodyMessage: String?): Builder {
            this.bodyMessage = bodyMessage
            return this
        }

        fun setContentIntent(contentIntent: PendingIntent?): Builder {
            this.contentIntent = contentIntent
            return this
        }

        fun setAutoCancel(isAutoCancel: Boolean?): Builder {
            this.isAutoCancel = isAutoCancel
            return this
        }

        fun create() {
            if (channel.isNotEmpty() && notificationName.isNotEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notificationChannel = NotificationChannel(
                        channel,
                        notificationName,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationManager?.createNotificationChannel(notificationChannel)
                }

                val createNotification = NotificationCompat.Builder(context, channel)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(bodyMessage)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(bodyMessage))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                if (contentIntent != null) {
                    createNotification.setContentIntent(contentIntent)
                } else {
                    val intent = Intent(context, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                    val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context)
                    stackBuilder.addParentStack(intent.javaClass)
                    stackBuilder.addNextIntent(intent)
                    contentIntent = stackBuilder.getPendingIntent(
                        0,
                        FLAG_UPDATE_CURRENT or FLAG_ONE_SHOT or FLAG_IMMUTABLE
                    )
                    createNotification.setContentIntent(contentIntent)
                }

                isAutoCancel?.let { createNotification.setAutoCancel(it) }

                notificationManager?.notify(notificationId, createNotification.build())
            }
        }
    }
}
