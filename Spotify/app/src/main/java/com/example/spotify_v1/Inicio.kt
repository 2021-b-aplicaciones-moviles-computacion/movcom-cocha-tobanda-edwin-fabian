package com.example.spotify_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class Inicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        val timer = object: CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                abrirActividad(Login::class.java)
            }
        }
        timer.start()
    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }
}