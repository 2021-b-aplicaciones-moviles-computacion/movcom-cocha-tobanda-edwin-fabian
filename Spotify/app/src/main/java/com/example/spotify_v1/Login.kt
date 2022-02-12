package com.example.spotify_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val botonLogin = findViewById<Button>(R.id.btn_login)
        val botonRegistro = findViewById<Button>(R.id.btn_create_account2)

        botonLogin.setOnClickListener { abrirActividad(BottomNavigationMenu::class.java) }
        botonRegistro.setOnClickListener { abrirActividad(BottomNavigationMenu::class.java) }
    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }
}