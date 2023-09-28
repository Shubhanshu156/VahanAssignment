package com.example.vahanassignment.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.vahanassignment.R
import com.example.vahanassignment.data.UniversityRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RunningService : Service() {
    private val scope = CoroutineScope(SupervisorJob())

    @Inject
    lateinit var repository: UniversityRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("RunningService", "Getting message for running service ")
        scope.launch {
            repository.getUniversityList()
        }

//        start()


        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        // Create the notification builder and build the notification here
        try{
//        if (Build.VERSION.SDK_INT >= 26) {
//            val CHANNEL_ID = "my_channel_01"
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                "Channel human readable title",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
//                channel
//            )
//            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("")
//                .setContentText("").build()
//            startForeground(1, notification)
//        startForeground(1, notification)
//        }
//        val notification = NotificationCompat.Builder(this, "alarm_id")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Refreshing Your Data")
//            .setContentText("Please Wait we are fetching Your Data")
//            .build()

        // Start the service in the foreground

        // Start any background tasks or coroutines as needed

    }
catch (e:Exception){
    Log.e("Running Service", "start: ${e.localizedMessage}", )
}
    }

    enum class Actions {
        START, STOP
    }

//    override fun onCreate() {
//
//        super.onCreate()
//        context startForeground(1, Notification())
//
//    }
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "my_channel_01"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setContentText("").build()
            startForeground(1, notification)
        }
    }
}
