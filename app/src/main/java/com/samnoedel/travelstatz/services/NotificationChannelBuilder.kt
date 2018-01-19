package com.samnoedel.travelstatz.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

/**
 * © 2018 Sam Noedel
 */

@Singleton
class NotificationChannelBuilder @Inject constructor(
        val notificationManager: NotificationManager
) {
    companion object {
        val NOTIFICATION_CHANNEL_ID = "TravelStatzChannel"
    }

    fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Travel Statz",
                NotificationManager.IMPORTANCE_DEFAULT)

        channel.description = "GPS status notifications"
        notificationManager.createNotificationChannel(channel)
    }
}
