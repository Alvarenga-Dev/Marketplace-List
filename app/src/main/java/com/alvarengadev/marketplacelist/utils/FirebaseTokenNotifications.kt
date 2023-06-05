package com.alvarengadev.marketplacelist.utils

import android.content.Context
import android.util.Log
import com.alvarengadev.marketplacelist.R
import com.alvarengadev.marketplacelist.utils.extensions.shortToast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

@Suppress("Unused")
object FirebaseTokenNotifications {
    fun getToken(context: Context) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Token failed", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val msg = context.getString(R.string.msg_token_fmt, task.result)
            Log.d("Token for tests", msg)
            context.shortToast(msg)
        })
    }
}
