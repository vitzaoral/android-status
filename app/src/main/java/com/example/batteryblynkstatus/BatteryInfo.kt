package com.example.batteryblynkstatus

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import java.net.URL


class BatteryInfo {

    companion object {
        private const val blynkToken = "TODO"

        fun sendData(context: Context) {
            // TODO: sila signalu https://stackoverflow.com/questions/58746879/how-to-get-dual-sim-signal-strength-using-kotlin
            // TODO: relase
            // TODO: GIT (a schovat blynk auth)

            // TODO: WiFi hotspot https://android.stackexchange.com/questions/91412/how-to-start-a-hotspot-automatically-on-device-boot
            val status = getData(context)

            try {
                Thread {
                    URL("http://blynk-cloud.com/${blynkToken}/update/V50?value=${status.level}").readText()
                    URL("http://blynk-cloud.com/${blynkToken}/update/V51?value=${status.currentCapacity}").readText()
                    URL("http://blynk-cloud.com/${blynkToken}/update/V52?value=${status.allCapacity}").readText()
                    Log.i("🟢", "OK")
                }.start()

            } catch (e: java.lang.Exception) {
                print(e)
                Log.i("🔴", e.toString())
            }
        }

        private fun getData(context: Context): BatteryStatus {
            val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, iFilter)

            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val batteryPct = level / scale.toDouble()

            val capacity = getBatteryCapacity((context)).toInt()
            val batLevelPercent = (batteryPct * 100).toInt()
            val batteryInMilliamps = (capacity * (batLevelPercent / 100.0)).toInt()

            return BatteryStatus(batLevelPercent, capacity, batteryInMilliamps)

            // Other version which can works on other devices
//
//            val capacity = getBatteryCapacity((context))
//            val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
//            val batLevelPercent = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
//            val batteryInMilliamps = capacity * (batLevelPercent / 100.0)
//
//            return BatteryStatus(batLevelPercent, capacity, batteryInMilliamps)
        }

        private fun getBatteryCapacity(context: Context?): Double {
            val mPowerProfile: Any
            var batteryCapacity = 0.0
            val power_profile_class = "com.android.internal.os.PowerProfile"
            try {
                mPowerProfile = Class.forName(power_profile_class)
                    .getConstructor(Context::class.java)
                    .newInstance(context)
                batteryCapacity = Class
                    .forName(power_profile_class)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile) as Double
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return batteryCapacity
        }
    }
}