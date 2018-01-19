package com.samnoedel.travelstatz

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.samnoedel.travelstatz.activity.MainActivity
import com.samnoedel.travelstatz.services.GpsService
import com.samnoedel.travelstatz.services.NotificationChannelBuilder
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * © 2017 Sam Noedel
 */
class TravelStatzService : Service() {

    companion object {
        private val TAG = "TravelStatzService"
        private val ONGOING_NOTIFICATION_ID = 1
        var isRunning = false
    }

    @Inject lateinit var gpsService: GpsService

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startForeground()
        gpsService.startGpsUpdates()
        isRunning = true
        return Service.START_STICKY
    }

    override fun onDestroy() {
        gpsService.stopGpsUpdates()
        isRunning = false
        super.onDestroy()
    }

    private fun startForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(this, NotificationChannelBuilder.NOTIFICATION_CHANNEL_ID)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setSmallIcon(R.drawable.notification_icon_background)
                .setVibrate(null)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message))
                .setContentIntent(pendingIntent)
                .build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }
}
