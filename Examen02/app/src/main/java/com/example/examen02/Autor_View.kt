package com.example.examen02

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Autor_View : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var nombreAutorSeleccionado = ""
    var idAutorSeleccionado = ""
    lateinit var autores: ArrayList<Autor>
    lateinit var adaptador: ArrayAdapter<Autor>
    lateinit var listAutoresView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor_view)
        listAutoresView = findViewById(R.id.listView_Autor)
        //-------------------------------------------------------
        val arregloAutores = mutableListOf<Autor>()
        val db = Firebase.firestore
        val referencia = db.collection("autor")
        referencia.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val autor = document.toObject(Autor::class.java)
                autor.id = document.id
                arregloAutores.add(autor)
                adaptador.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.i("firebase-firestore", "Error leyendo coleccion")
        }
        autores = arregloAutores as ArrayList<Autor>
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, autores)
        //-------------------------------------------------

        listAutoresView.adapter = adaptador
        val botonCrearAutor = findViewById<Button>(R.id.btn_crear_Autor)
        val botonVerLibros = findViewById<Button>(R.id.btn_verLibros)
        botonVerLibros.isEnabled = false //Desactivo botón hasta seleccionar
        botonCrearAutor.setOnClickListener { abrirActividad(CrearAutor::class.java) }
        registerForContextMenu(listAutoresView)
        val infoAutor = findViewById<TextView>(R.id.txt_InfoAutor)
        adaptador.notifyDataSetChanged()
        listAutoresView.setOnItemClickListener { _, _, position, _ ->
            botonVerLibros.isEnabled = true
            val autorSelec = listAutoresView.getItemAtPosition(position) as Autor
            infoAutor.text = autorSelec.imprimirDatosAutor()
            botonVerLibros.setOnClickListener {
                abrirActividadConParametros(
                    autorSelec.nombre!!,
                    autorSelec.id!!,
                    Libros_View::class.java
                )
            }
            return@setOnItemClickListener
        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionItemSeleccionado = info.position

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        adaptador.notifyDataSetChanged()
        val db = Firebase.firestore
        val referencia = db.collection("autor")
        val autorSeleccionado = listAutoresView.getItemAtPosition(posicionItemSeleccionado) as Autor
        nombreAutorSeleccionado = autorSeleccionado.nombre!!
        idAutorSeleccionado = autorSeleccionado.id!!
        listAutoresView.adapter = adaptador
        val cancelarClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_SHORT).show()
        }
        val eliminarClick = { _: DialogInterface, _: Int ->
            Log.i("FIREBASE", "Nombre autor: $nombreAutorSeleccionado")
            //AQUI DEBO ELIMINAR
            referencia.document(idAutorSeleccionado)
                .delete()
                .addOnSuccessListener { Log.i("firebase", "Document Snapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.i("firebase", "Error deleting document", e) }
            autores.remove(autorSeleccionado)
            adaptador.notifyDataSetChanged()
            Toast.makeText(this, "Autor Eliminado", Toast.LENGTH_SHORT).show()
        }
        return when (item.itemId) {
            R.id.menu_editar -> {
                abrirActividadConParametros(nombreAutorSeleccionado, idAutorSeleccionado, CrearAutor::class.java)
                return true
            }
            R.id.menu_eliminar -> {
                val advertencia = AlertDialog.Builder(this)
                advertencia.setTitle("Eliminar")
                advertencia.setMessage("Seguro de eliminar?")
                advertencia.setNegativeButton(
                    "Cancelar",
                    DialogInterface.OnClickListener(function = cancelarClick)
                )
                advertencia.setPositiveButton(
                    "Eliminar", DialogInterface.OnClickListener(
                        function = eliminarClick
                    )
                )
                advertencia.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    private fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }

    private fun abrirActividadConParametros(autor: String, idAutor: String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreAutor", autor)
        intentExplicito.putExtra("idAutor", idAutor)
        startActivity(intentExplicito)
    }

}