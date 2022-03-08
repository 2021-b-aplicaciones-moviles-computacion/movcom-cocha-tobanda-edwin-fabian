package com.example.examen02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearAutor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_autor)
        val nombre = findViewById<TextView>(R.id.txt_ingresoNombre)
        val nacionalidad = findViewById<TextView>(R.id.txt_ingresoNacionalidad)
        val edadA = findViewById<TextView>(R.id.txt_ingresoEdadA)
        val guardar = findViewById<Button>(R.id.btn_guardarAutor)

        val nombreAutor = intent.getStringExtra("nombreAutor") //Pa Editar
        val idAutor = intent.getStringExtra("idAutor")

        //Si no pasamos NombreDirector es Creacion, caso contrario es Actualizacion
        if (nombreAutor != null) {
            //Cambio Título de la Actividad de Registrar a Actualizar
            val tituloActualizarAutor = findViewById<TextView>(R.id.txt_registroAutor)
            tituloActualizarAutor.text = getString(R.string.actualizar_Autor_Titulo)
            //Obtengo Datos actuales del Director y los paso a TextFields
            val db = Firebase.firestore
            val referencia = db.collection("autor").document(idAutor!!)
            referencia.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val autorParaEditar = document.toObject(Autor::class.java)
                        nombre.text = autorParaEditar?.nombre
                        nacionalidad.text = autorParaEditar?.nacionalidad
                        edadA.text = autorParaEditar?.edad.toString()
                    } else {
                        Log.d("firebase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebase", "get failed with ", exception)
                }


            guardar.setOnClickListener {
                val nombreActualizado = nombre.text.toString()
                val nacionalidadActualizada = nacionalidad.text.toString()
                val edadActualizada = edadA.text.toString()

                if (!revisarTextLlenos(nombreActualizado, nacionalidadActualizada, edadActualizada)) {
                    val autorActualizado = hashMapOf<String, Any>(
                        "nombre" to nombreActualizado,
                        "nacionalidad" to nacionalidadActualizada,
                        "edad" to edadActualizada.toInt()
                    )
                    referencia.update(autorActualizado)
                        .addOnSuccessListener { Log.i("firebase", "Transaccion completa") }
                        .addOnFailureListener { Log.i("firebase", "Error al Actualizar") }


                    Toast.makeText(this, "Autor actualizado", Toast.LENGTH_SHORT).show()
                    abrirActividad(Autor_View::class.java)
                }
            }

        } else {
            guardar.setOnClickListener {
                val nombreAGuardar = nombre.text.toString()
                val nacionalidadAGuardar = nacionalidad.text.toString()
                val edadAGuardar = edadA.text.toString()

                if (!revisarTextLlenos(nombreAGuardar, nacionalidadAGuardar, edadAGuardar)) {
                    val db = Firebase.firestore
                    val nuevoAutor = hashMapOf<String, Any>(
                        "nombre" to nombreAGuardar,
                        "nacionalidad" to nacionalidadAGuardar,
                        "edad" to edadAGuardar.toInt()
                    )
                    db.collection("autor").add(nuevoAutor)
                        .addOnSuccessListener {
                            Log.i("firebase-firestore", "Autor añadido")
                        }.addOnFailureListener {
                            Log.i("firebase-firestore", "Error al añadir Autor")
                        }
                    Toast.makeText(this, "Autor guardado", Toast.LENGTH_SHORT).show()
                    abrirActividad(Autor_View::class.java)
                }
            }
        }
    }

    private fun  revisarTextLlenos(nombre: String, nacionalidad: String, edadA: String): Boolean {
        return if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(nacionalidad) || TextUtils.isEmpty(edadA)) {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            true
        } else {
            if(edadA.toInt()<0 || edadA.toInt()>100){
                Toast.makeText(this, "Ingrese valores válidos", Toast.LENGTH_SHORT).show()
                true
            }else{
                false
            }
        }

    }


    private fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }

}