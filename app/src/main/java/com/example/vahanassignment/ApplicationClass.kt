package com.example.vahanassignment

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "alarm_id",
                "Running Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationmananger=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationmananger.createNotificationChannel(channel)
        }
    }
}