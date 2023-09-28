package com.example.vahanassignment.AlarmManager

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.vahanassignment.R
import com.example.vahanassignment.Service.RunningService
import com.example.vahanassignment.data.UniversityRepository
import javax.inject.Inject


class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repository: UniversityRepository
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) {
            return
        }
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        val channelId = "alarm_id"

        context?.let { ctx ->
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Fetching Data")
                .setContentText("Please wait we are fetching your data $message")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            notificationManager.notify(1, builder.build())
        }
        Log.d("Reciving broadcast message", "onReceive: I am receiving message please check it")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(Intent(context, RunningService::class.java))
        } else {
            context!!.startService(Intent(context, RunningService::class.java))
        }
//        val serviceIntent = Intent(context, RunningService::class.java)
//        serviceIntent.action = RunningService.Actions.START.toString()
//        context?.startService(serviceIntent)

    }

}