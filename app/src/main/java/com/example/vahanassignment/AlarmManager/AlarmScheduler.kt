package com.example.vahanassignment.AlarmManager

import java.time.LocalDateTime

interface AlarmScheduler {
fun schedule(alarmItem: AlarmItem)
fun cancel(alarmItem: AlarmItem)
}
data class AlarmItem(
    val alarmTime : LocalDateTime,
    val message : String
)