package com.mediocre

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Paclass: Application() {
    companion object {
        const val AF_DEV_KEY = "TaLNYqCmzAGfxzRTj8yp6P"
        const val jsoupCheck = "1n6x"
        const val ONESIGNAL_APP_ID = "d6fbbd12-874f-45d2-a3c7-2521511b0ff9"

        val linkFilterPart1 = "http://amber"
        val linkFilterPart2 = "gold.xyz/go.php?to=1&"
        val linkAppsCheckPart1 = "http://amber"
        val linkAppsCheckPart2 = "gold.xyz/apps.txt"

        val odone = "sub_id_1="

        var MAIN_ID: String? = ""
        var C1: String? = "c11"
        var D1: String? = "d11"
        var CH: String? = "check"

    }

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch(Dispatchers.IO) {
            applyDeviceId(context = applicationContext)
        }
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

    private suspend fun applyDeviceId(context: Context) {
        val advertisingInfo = Adv(context)
        val idInfo = advertisingInfo.getAdvertisingId()

        val prefs = getSharedPreferences("SP", MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putString(MAIN_ID, idInfo)
        editor.apply()
    }

}

class Adv (context: Context) {
    private val adInfo = AdvertisingIdClient(context.applicationContext)

    suspend fun getAdvertisingId(): String =
        withContext(Dispatchers.IO) {
            adInfo.start()
            val adIdInfo = adInfo.info
            Log.d("getAdvertisingId = ", adIdInfo.id.toString())
            adIdInfo.id
        }
}