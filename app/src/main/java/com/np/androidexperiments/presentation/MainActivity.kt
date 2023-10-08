package com.np.androidexperiments.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.np.androidexperiments.R
import com.np.androidexperiments.presentation.design_system.AppTheme
import com.np.kmm_test.Greeting
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val notifBuilder =
        NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(null)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    override fun onCreate(savedInstanceState: Bundle?) {
        createNotificationChannel()
        super.onCreate(savedInstanceState)

        Log.d("1231231", intent.extras?.get("uuuu").toString() ?: "error")

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("123123", "Fetching FCM registration token failed", task.exception)
            } else {
                // Get new FCM registration token
                val token = task.result

                // Log and toast
                Log.d("123123", token)
            }
        }

        setContent {
            var dyslexicMode by remember { mutableStateOf(false) }
            val greeting = remember { Greeting().greet() }

            AppTheme(dyslexicMode) {
                val context = LocalContext.current
                val state by viewModel.state.collectAsState()

                LaunchedEffect(key1 = Unit) {
                    viewModel.action.collect { action ->
                        when (action) {
                            is MainAction.Notification -> {
                                val notif =
                                    notifBuilder.setContentText(action.msg)
                                        .setContentTitle(action.title).build()
                                NotificationManagerCompat.from(context)
                                    .notify(Random.nextInt(), notif)
                            }

                            is MainAction.Toast -> {
                                Toast.makeText(context, action.msg, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Button(onClick = { dyslexicMode = !dyslexicMode }) {
                        Text(text = "Dyslexic mode: $dyslexicMode")
                    }

                    Text(text = "A Taxt to check afyg 123 ?:>^& текст для перевірки афн", style = AppTheme.typography.heading1)
                    Text(text = greeting, style = AppTheme.typography.heading1)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        val name = "channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("1", name, importance)
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
