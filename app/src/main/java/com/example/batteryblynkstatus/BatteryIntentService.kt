package com.example.batteryblynkstatus

import android.app.IntentService
import android.content.Intent

class BatteryIntentService : IntentService("BatteryIntentService") {
    override fun onHandleIntent(intent: Intent?) { // Do the task here
        BatteryInfo.sendData(applicationContext)
    }
}