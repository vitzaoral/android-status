package com.example.batteryblynkstatus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class BatteryReceiver : BroadcastReceiver() {
    companion object {
        const val REQUEST_CODE = 12345
    }

    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, BatteryIntentService::class.java)
        context.startService(i)
    }
}