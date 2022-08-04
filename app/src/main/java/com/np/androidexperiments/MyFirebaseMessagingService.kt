package com.np.androidexperiments

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val notifBuilder =
        NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(null)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notif =
            notifBuilder.setContentText(message.notification?.body).setContentTitle(message.notification?.title).build()
        NotificationManagerCompat.from(this).notify(Random.nextInt(), notif)

        Log.d("123123", "From: ${message.from} \n ${message.data} \n ${message.notification?.body}")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("123123", token)
    }
}