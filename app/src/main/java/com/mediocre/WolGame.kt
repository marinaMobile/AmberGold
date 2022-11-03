package com.mediocre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mediocre.databinding.ActivityWolGameBinding

class WolGame : AppCompatActivity() {
    val roll = mutableListOf<String>("1", "2", "3")
    lateinit var bindGamee: ActivityWolGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindGamee = ActivityWolGameBinding.inflate(layoutInflater)
        setContentView(bindGamee.root)
        var one: String = ""
        var two: String = ""
        var three: String = ""

        var first: ImageView = bindGamee.first
        var second: ImageView = bindGamee.second
        var third: ImageView = bindGamee.third

        val s: Long = "5".toLong() * 1000

        object : CountDownTimer(s, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                bindGamee.running.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                MaterialAlertDialogBuilder(this@WolGame)
                    .setTitle("Time's Up")
                    .setCancelable(false)
                    .setPositiveButton("Play again") { dialog, _ ->

                        startActivity(Intent(applicationContext, WolGame::class.java))
                        finish()
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }.start()



        bindGamee.button2.setOnClickListener {
            one = roll.random()
            check(one, first)
            check(three, third)
            check(two, second)
            checkWin(one, two, three)
        }

        bindGamee.button1.setOnClickListener {
            two = roll.random()
            check(one, first)
            check(three, third)
            check(two, second)
            checkWin(one, two, three)

        }
        bindGamee.button3.setOnClickListener {
            three = roll.random()
            check(one, first)
            check(three, third)
            check(two, second)
            checkWin(one, two, three)
        }

    }

    fun check(i: String, im: ImageView) {
        if (i == "1") {
            im.setImageResource(R.drawable.one)
        } else if (i == "2") {
            im.setImageResource(R.drawable.two)
        } else if (i == "3") {
            im.setImageResource(R.drawable.three)
        }
    }

    fun checkWin(i: String, i1: String, i2: String) {
        if (i == "1" && i1 == "1" && i2 == "1") {
            intentr()
        } else if (i == "2" && i1 == "2" && i2 == "2") {
            intentr()

        } else if (i == "3" && i1 == "3" && i2 == "3") {
            intentr()
        }
    }


    fun intentr() {
        val intent = Intent(this, WolGame::class.java)
        startActivity(intent)
    }
}