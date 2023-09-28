package com.example.vahanassignment.AlarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId

class AlarmSchedulerImp(
    private val context: Context
) : AlarmScheduler{
    private val alarmManager = context.getSystemService(AlarmManager::class.java)




    @RequiresApi(Build.VERSION_CODES.O)
    override fun schedule(alarmItem: AlarmItem) {
        val intent= Intent(context,AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE",alarmItem.message)
        }
        val alarmTime = alarmItem.alarmTime.atZone(ZoneId.systemDefault()).toEpochSecond()
        alarmManager.setRepeating(
            AlarmManager.RTC,alarmTime,10000,
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(alarmItem: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}