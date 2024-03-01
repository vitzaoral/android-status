package com.example.batteryblynkstatus

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // run service as Alarm
        scheduleAlarm()

        BatteryInfo.sendData(applicationContext)
    }

    // Setup a recurring alarm every half hour
    // https://guides.codepath.com/android/Starting-Background-Services#using-with-alarmmanager-for-periodic-tasks
    private fun scheduleAlarm() { // Construct an intent that will execute the AlarmReceiver
        val intent = Intent(applicationContext, BatteryReceiver::class.java)
        // Create a PendingIntent to be triggered when the alarm goes off
        val pIntent = PendingIntent.getBroadcast(this, BatteryReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // Setup periodic alarm every half hour from this point onwards
        val firstMillis = SystemClock.elapsedRealtime() // alarm is set right away
        val alarm = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            firstMillis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pIntent
        )
    }
}
