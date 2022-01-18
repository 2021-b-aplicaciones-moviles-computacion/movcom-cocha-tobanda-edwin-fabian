package com.example.examen01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btIniciar= findViewById<TextView>(R.id.bt_iniciar)
        btIniciar.setOnClickListener {
            abrirActividad(Autor_View::class.java)
        }
    }
    private fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }
}