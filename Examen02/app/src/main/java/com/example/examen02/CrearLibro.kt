package com.example.examen02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearLibro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_libro)
        val nombreAutor = intent.getStringExtra("nombreAutor")
        val idAutor = intent.getStringExtra("idAutor")
        val idLibro = intent.getStringExtra("idLibro")
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
            builder.setCancelable(false)//NO se puede cancelar
            builder.setMultiChoiceItems(
                opciones, generosSeleccionados
            ) { _, which, isChecked ->
                if (isChecked) {
                    selectGeneros.add(which)
                    selectGeneros.sort()
                } else {
                    selectGeneros.remove(which)
                }
                Log.i("firestore", "$which $isChecked")
            }
            builder.setPositiveButton("OK") { _, _ ->
                var generos = ""
                var conca = ""
                if (selectGeneros.size > 1) {conca = "/"}
                selectGeneros.forEach { i -> generos = generos + opciones[i] + conca }
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
            //Cambio Título de la Actividad de Registrar a Actualizar
            val tituloActualizarAutor = findViewById<TextView>(R.id.txt_registroLibro)
            tituloActualizarAutor.text = getString(R.string.actualizar_Libro_Titulo)
            //Obtengo Datos actuales de la Película y los paso a TextFields
            val db = Firebase.firestore
            val referencia = db.collection("libro").document(idLibro!!)
            referencia.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val libroParaEditar = document.toObject(Libro::class.java)
                        txttituloLibro.text = libroParaEditar?.titulo
                        txtGenero.text = libroParaEditar?.genero
                        txtAnioP.text = libroParaEditar?.anioPubli.toString()
                        txtDisponibles.text = libroParaEditar?.disponibles.toString()
                        txtPrecio.text = libroParaEditar?.precio.toString()
                    } else {
                        Log.d("firebase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebase", "get failed with ", exception)
                }

            guardar.setOnClickListener {
                val tituloActualizado = txttituloLibro.text.toString()
                val anioActualizado = txtAnioP.text.toString()
                //val disponiblesActualizado = txtDisponibles.text.toString()
                val disponiblesActualizado = txtDisponibles.text.toString()
                val precioActualizado = txtPrecio.text.toString()
                //Reviso si los TextFields están llenos
                if (!revisarText(tituloActualizado, txtGenero, anioActualizado, disponiblesActualizado, precioActualizado)) {
                    val peliculaActualizada = hashMapOf<String, Any>(
                        "titulo" to tituloActualizado,
                        "genero" to txtGenero.text.toString(),
                        "anioPubli" to anioActualizado.toInt(),
                        "disponibles" to disponiblesActualizado.toInt(),
                        "precio" to precioActualizado.toFloat()
                    )
                    referencia.update(peliculaActualizada)
                        .addOnSuccessListener { Log.i("firebase", "Transaccion completa") }
                        .addOnFailureListener { Log.i("firebase", "ERROR al actualizar") }


                    Toast.makeText(this, "Libro Actualizado", Toast.LENGTH_SHORT).show()
                    if (nombreAutor != null) {
                        abrirActividadConParametros(nombreAutor, idAutor!!, Libros_View::class.java)
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
                    val db = Firebase.firestore

                    val nuevoLibro = hashMapOf<String, Any>(
                        "autor" to idAutor!!,
                        "titulo" to tituloGuardar,
                        "genero" to txtGenero.text.toString(),
                        "anioPubli" to anioPGuardar.toInt(),
                        "disponibles" to disponiblesGuardar.toInt(),
                        "precio" to precioGuardar.toFloat()

                    )
                    db.collection("libro").add(nuevoLibro)
                        .addOnSuccessListener {
                            Log.i("firebase-firestore", "Libro añadido")
                        }.addOnFailureListener {
                            Log.i("firebase-firestore", "Error al añadir Libro")
                        }

                    Toast.makeText(this, "Libro guardado", Toast.LENGTH_SHORT).show()
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

    private fun abrirActividadConParametros(autor: String, idAutor: String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreAutor", autor)
        intentExplicito.putExtra("idAutor", idAutor)
        startActivity(intentExplicito)
    }
}