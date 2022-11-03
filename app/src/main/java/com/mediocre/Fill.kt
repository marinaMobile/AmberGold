package com.mediocre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mediocre.Paclass.Companion.C1
import com.mediocre.Paclass.Companion.D1
import com.mediocre.Paclass.Companion.jsoupCheck
import com.mediocre.Paclass.Companion.linkFilterPart1
import com.mediocre.Paclass.Companion.linkFilterPart2
import com.mediocre.Paclass.Companion.odone
import kotlinx.coroutines.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class Fill : AppCompatActivity() {
    lateinit var jsoup: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill)
        jsoup = ""

        val job = GlobalScope.launch(Dispatchers.IO) {
            jsoup = coTask()
            Log.d("jsoup status from global scope", jsoup)
        }

        runBlocking {
            try {
                job.join()

                Log.d("jsoup status out of global scope", jsoup)


                if (jsoup == jsoupCheck) {
                    Intent(applicationContext, WolGame::class.java).also { startActivity(it) }
                } else {
                    Intent(applicationContext, Weeee::class.java).also { startActivity(it) }
                }
                finish()
            } catch (e: Exception) {

            }
        }
    }

    private suspend fun coTask(): String {
        val sharedPref = getSharedPreferences("SP", MODE_PRIVATE)

        val nameParameter: String? = sharedPref.getString(C1, null)
        val appLinkParameter: String? = sharedPref.getString(D1, null)


        val taskName = "${linkFilterPart1}${linkFilterPart2}${odone}$nameParameter"
        val taskLink = "${linkFilterPart1}${linkFilterPart2}${odone}$appLinkParameter"

        withContext(Dispatchers.IO) {
            //changed logical null to string null
            if (nameParameter != "null") {
                getTheCypher(taskName)
                Log.d("Check1C", taskName)
            } else {
                getTheCypher(taskLink)
                Log.d("Check1C", taskLink)
            }
        }
        return jsoup
    }
    private fun getTheCypher(link: String) {
        val url = URL(link)
        val urlConnection = url.openConnection() as HttpURLConnection

        try {
            val text = urlConnection.inputStream.bufferedReader().readText()
            if (text.isNotEmpty()) {
                jsoup = text
            }
        } catch (ex: Exception) {

        } finally {
            urlConnection.disconnect()
        }
    }
}