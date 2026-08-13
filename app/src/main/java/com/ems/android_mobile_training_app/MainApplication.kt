package com.ems.android_mobile_training_app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.emarsys.Emarsys
import com.emarsys.config.EmarsysConfig
import com.emarsys.mobileengage.api.event.EventHandler
import org.json.JSONObject

class MainApplication : Application(), EventHandler {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()

        val config = EmarsysConfig(application=this,
            applicationCode="EMSCF-E601F"
        )

        Emarsys.setup(config)

        Emarsys.push.setNotificationEventHandler(this)
        Emarsys.push.setSilentMessageEventHandler(this)
        Emarsys.inApp.setEventHandler(this)
        Emarsys.onEventAction.setOnEventActionEventHandler(this)
        Emarsys.geofence.setEventHandler(this)
    }

// https://help.emarsys.com/hc/en-us/articles/360003269493-Android-integration-Mobile-Engage-Android-notification-channels
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel(
                "ems_sample_messages",
                "Messages",
                "Important messages go into this channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            createNotificationChannel(
                "ems_sample_news",
                "News",
                "Important messages go into this channel",
                NotificationManager.IMPORTANCE_HIGH
            )
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        id: String,
        name: String,
        description: String,
        importance: Int
    ) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(id, name, importance)
        channel.description = description
        notificationManager.createNotificationChannel(channel)
    }

    override fun handleEvent(context: Context, eventName: String, payload: JSONObject?) {
        Toast.makeText(context, "Push tapped! $eventName | $payload", Toast.LENGTH_SHORT).show()
    }
}