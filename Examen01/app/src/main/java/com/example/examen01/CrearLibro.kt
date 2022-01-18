package com.example.examen01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlin.collections.ArrayList

class CrearLibro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_libro)
        val nombreAutor = intent.getStringExtra("nombreAutor")
        val idAutor = intent.getIntExtra("idAutor", 0)
        val txtAutor = findViewById<TextView>(R.id.txt_nombreAutor)
        txtAutor.text = nombreAutor

        val txttituloLibro = findViewById<TextView>(R.id.txt_tituloLibro)
        val txtGenero = findViewById<TextView>(R.id.txt_selectGenero)
        val txtAnioP = findViewById<TextView>(R.id.txt_ingresoAnioP)
        val txtDisponibles = findViewById<TextView>(R.id.txt_disponibles)
        val txtPrecio = findViewById<TextView>(R.id.txt_precio)
        val guardar = findViewById<Button>(R.id.btn_guardarLibro)
        val selectGeneros = ArrayList<Int>()
        val generosSeleccionados = BooleanArray(12) { false }
        txtGenero.setOnClickListener {
            val opciones = resources.getStringArray(R.array.generos_dialogo)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Géneros")
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                opciones, generosSeleccionados
            ) { _, which, isChecked ->
                if (isChecked) {
                    selectGeneros.add(which)
                    selectGeneros.sort()
                } else {
                    selectGeneros.remove(which)
                }
                Log.i("bdd", "$which $isChecked")
            }
            builder.setPositiveButton("OK") { _, _ ->
                var generos = ""
                selectGeneros.forEach { i -> generos = generos + opciones[i] + "/" }
                if (generos == "") {
                    txtGenero.text = getString(R.string.seleccione)
                } else {
                    txtGenero.text = generos
                }
            }
            builder.show()
        }

        val tituloLibro = intent.getStringExtra("tituloLibro")

        //Si no pasamos TituloLibro es Creacion, caso contrario es Actualizacion
        if (tituloLibro != null && tituloLibro != "") {
            Log.i("bdd","$tituloLibro")
            //Cambio Título de la Actividad de Registrar a Actualizar
            val tituloActualizarAutor = findViewById<TextView>(R.id.txt_registroLibro)
            tituloActualizarAutor.text = getString(R.string.actualizar_Libro_Titulo)
            //Obtengo Datos actuales del Libro y los paso a TextFields
            val libroParaEditar = BaseDatos.Tablas!!.consultarLibroPorTitulo(tituloLibro)
            val idActualizar = libroParaEditar.id
            txttituloLibro.text = libroParaEditar.titulo
            txtGenero.text = libroParaEditar.genero
            txtAnioP.text = libroParaEditar.anioPubli.toString()
            txtDisponibles.text = libroParaEditar.disponibles.toString()
            txtPrecio.text = libroParaEditar.precio.toString()

            guardar.setOnClickListener {
                val tituloActualizado = txttituloLibro.text.toString()
                val anioActualizado = txtAnioP.text.toString()
                val disponiblesActualizado = txtDisponibles.text.toString()
                val precioActualizado = txtPrecio.text.toString()
                //Reviso si los TextFields están llenos
                if (!revisarText(tituloActualizado, txtGenero, anioActualizado, disponiblesActualizado, precioActualizado)) {
                    BaseDatos.Tablas!!.actualizarLibro(
                        tituloActualizado, txtGenero.text.toString(),anioActualizado.toInt(),
                        disponiblesActualizado.toInt(), precioActualizado.toFloat(), idActualizar
                    )
                    Toast.makeText(this, "Libro Actualizado", Toast.LENGTH_SHORT).show()
                    if (nombreAutor != null) {
                        abrirActividadConParametros(nombreAutor, idAutor, Libros_View::class.java)
                    }
                }
            }

        } else {
            guardar.setOnClickListener {
                val tituloGuardar = txttituloLibro.text.toString()
                val anioPGuardar = txtAnioP.text.toString()
                val disponiblesGuardar = txtDisponibles.text.toString()
                val precioGuardar = txtPrecio.text.toString()
                if (!revisarText(tituloGuardar, txtGenero, anioPGuardar, disponiblesGuardar, precioGuardar)) {
                    BaseDatos.Tablas!!.crearLibro(
                        tituloGuardar, txtGenero.text.toString(), anioPGuardar.toInt(),
                        disponiblesGuardar.toInt(), precioGuardar.toFloat(), idAutor
                    )
                    Toast.makeText(this, "Libro Guardado", Toast.LENGTH_SHORT).show()
                    if (nombreAutor != null) {
                        abrirActividadConParametros(nombreAutor, idAutor, Libros_View::class.java)
                    }
                }
            }
        }
    }

    private fun revisarText(titulo: String, genero: TextView, anioP: String, disponibles: String, precio: String): Boolean {
        return if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(anioP) || TextUtils.isEmpty(disponibles) || TextUtils.isEmpty(precio) || genero.text.equals("Seleccione:")) {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            true
        } else {
            if (anioP.toInt() > 2022 || anioP.toInt() < 1 ) {
                Toast.makeText(this, "Ingrese valores válidos", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }
    }

    private fun abrirActividadConParametros(autor: String, idAutor: Int, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreAutor", autor)
        intentExplicito.putExtra("idAutor", idAutor)
        startActivity(intentExplicito)
    }

}