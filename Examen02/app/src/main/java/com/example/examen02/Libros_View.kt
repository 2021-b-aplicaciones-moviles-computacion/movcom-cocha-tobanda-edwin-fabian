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

class Libros_View : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var tituloLibroSeleccionado = ""
    var idLibroSeleccionado = ""
    var nombreAutor = ""
    var idAutor = ""
    lateinit var libros :ArrayList<Libro>
    lateinit var adaptador: ArrayAdapter<Libro>
    lateinit var listLibrosView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libros_view)
        idAutor = intent.getStringExtra("idAutor").toString()
        listLibrosView = findViewById(R.id.listview_Libros)
        //--------------------------------------------------------------------
        val arregloLibros = mutableListOf<Libro>()
        val db = Firebase.firestore
        val referencia = db.collection("libro")
        referencia.whereEqualTo("autor", idAutor).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val libro = document.toObject(Libro::class.java)
                libro.id = document.id
                arregloLibros.add(libro)
                adaptador.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.i("firebase-firestore", "Error leyendo coleccion")
        }
        libros= arregloLibros as ArrayList<Libro>
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1,libros)
        //---------------------------------------------------------------------
        listLibrosView.adapter = adaptador
        val txtAutor = findViewById<TextView>(R.id.txt_NombreAutorLibro)
        nombreAutor = intent.getStringExtra("nombreAutor").toString()

        txtAutor.text = nombreAutor
        val botonCrearLibro = findViewById<Button>(R.id.btn_crear_Libro)
        botonCrearLibro.setOnClickListener {
            abrirActividadConParametros(nombreAutor, idAutor, "","", CrearLibro::class.java)
        }
        registerForContextMenu(listLibrosView)
        val infoLibro = findViewById<TextView>(R.id.txt_infoLibros)
        listLibrosView.setOnItemClickListener { _, _, position, _ ->
            val libroSelect = listLibrosView.getItemAtPosition(position) as Libro

            infoLibro.text = libroSelect.imprimirDatosLibro()

            return@setOnItemClickListener
        }
        val btvolverAutores= findViewById<TextView>(R.id.bt_Autores)
        btvolverAutores.setOnClickListener {
            abrirActividad(Autor_View::class.java)
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
        val info = menuInfo as AdapterView.AdapterContextMenuInfo //AS cast
        posicionItemSeleccionado = info.position

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        adaptador.notifyDataSetChanged()
        val db = Firebase.firestore
        val referencia = db.collection("libro")
        val libroSeleccionado = listLibrosView.getItemAtPosition(posicionItemSeleccionado) as Libro
        tituloLibroSeleccionado = libroSeleccionado.titulo!!
        idLibroSeleccionado = libroSeleccionado.id!!
        listLibrosView.adapter = adaptador
        val cancelarClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_SHORT).show()
        }
        val eliminarClick = { _: DialogInterface, _: Int ->
            Log.i("firebase", "Titulo Libro: $tituloLibroSeleccionado")
            referencia.document(idLibroSeleccionado)
                .delete()
                .addOnSuccessListener { Log.i("firebase", "Document Snapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.i("firebase", "Error deleting document", e) }
            libros.remove(libroSeleccionado)
            adaptador.notifyDataSetChanged()
            Toast.makeText(this, "Libro Eliminado", Toast.LENGTH_SHORT).show()
        }
        return when (item.itemId) {
            R.id.menu_editar -> {
                abrirActividadConParametros(nombreAutor, idAutor, tituloLibroSeleccionado,idLibroSeleccionado, CrearLibro::class.java)
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


    private fun abrirActividadConParametros(autor: String, idAutor: String, titulo: String, idLibro:String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreAutor", autor)
        intentExplicito.putExtra("idAutor", idAutor)
        intentExplicito.putExtra("idLibro", idLibro)
        intentExplicito.putExtra("tituloLibro", titulo)
        startActivity(intentExplicito)
    }
    private fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }
}