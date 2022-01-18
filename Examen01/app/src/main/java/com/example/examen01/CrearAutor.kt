package com.example.examen01


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class CrearAutor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseDatos.Tablas = SQLiteHelper(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_autor)
        val nombre = findViewById<TextView>(R.id.txt_ingresoNombre)
        val nacionalidad = findViewById<TextView>(R.id.txt_ingresoNacionalidad)
        val edadA = findViewById<TextView>(R.id.txt_ingresoEdadA)
        val guardar = findViewById<Button>(R.id.btn_guardarDir)

        val nombreAutor = intent.getStringExtra("nombreAutor")

        //Si no pasamos NombreAutor es una Creacion, caso contrario es Actualizacion
        if (nombreAutor != null) {
            //Cambio Título de la Actividad de Registrar a Actualizar
            val tituloActualizarAutor = findViewById<TextView>(R.id.txt_registroAutor)
            tituloActualizarAutor.text = getString(R.string.actualizar_Autor_Titulo)
            //Obtengo Datos actuales del Autor y los paso a TextFields
            val autorParaEditar = BaseDatos.Tablas!!.consultarAutorPorNombre(nombreAutor)
            val idActualizar = autorParaEditar.id
            nombre.text = autorParaEditar.nombre
            nacionalidad.text = autorParaEditar.nacionalidad
            edadA.text = autorParaEditar.edad.toString()

            guardar.setOnClickListener {
                val nombreActualizado = nombre.text.toString()
                val nacionalidadActualizada = nacionalidad.text.toString()
                val edadAAct = edadA.text.toString()
                //Reviso si los TextFields están llenos
                if (!revisarTextLlenos(nombreActualizado, nacionalidadActualizada, edadAAct)) {
                    BaseDatos.Tablas!!.actualizarAutor(
                        nombreActualizado,
                        nacionalidadActualizada,
                        edadAAct.toInt(),
                        idActualizar
                    )
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
                    BaseDatos.Tablas!!.crearAutor(
                        nombreAGuardar,
                        nacionalidadAGuardar,
                        edadAGuardar.toInt()
                    )
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