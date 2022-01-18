package com.example.examen01

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

class Libros_View : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var tituloLibroSeleccionado = ""
    var nombreAutor = ""
    var idAutor = 0
    lateinit var libros :ArrayList<Libro>
    lateinit var adaptador: ArrayAdapter<Libro>
    lateinit var listLibrosView: ListView

    override fun onStart() {
        super.onStart()
        BaseDatos.Tablas = SQLiteHelper(this)
        libros = BaseDatos.Tablas!!.consultarLibrosAutor(idAutor)
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, libros)
        listLibrosView = findViewById<ListView>(R.id.listview_Libros)
        adaptador.notifyDataSetChanged()
        listLibrosView.adapter = adaptador

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libros_view)
        val txtAutor = findViewById<TextView>(R.id.txt_NombreAutorLibro)
        nombreAutor = intent.getStringExtra("nombreAutor").toString()
        idAutor = intent.getIntExtra("idAutor", 0)
        txtAutor.text = nombreAutor
        onStart()
        val botonCrearLibro = findViewById<Button>(R.id.btn_crear_Libro)
        botonCrearLibro.setOnClickListener {
            abrirActividadConParametros(nombreAutor, idAutor, "", CrearLibro::class.java)
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

        BaseDatos.Tablas = SQLiteHelper(this)
        onStart()
        val libroSeleccionado = listLibrosView.getItemAtPosition(posicionItemSeleccionado) as Libro
        tituloLibroSeleccionado = libroSeleccionado.titulo
        listLibrosView.adapter = adaptador
        val cancelarClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_SHORT).show()
        }
        val eliminarClick = { _: DialogInterface, _: Int ->
            Log.i("bdd", "Titulo Libro: $tituloLibroSeleccionado")
            BaseDatos.Tablas!!.eliminarLibro(tituloLibroSeleccionado)
            onStart()
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
        }
        return when (item.itemId) {
            R.id.menu_editar -> {
                abrirActividadConParametros(nombreAutor, idAutor, tituloLibroSeleccionado, CrearLibro::class.java)
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

    private fun abrirActividadConParametros(autor: String, idAutor: Int, titulo: String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreAutor", autor)
        intentExplicito.putExtra("idAutor", idAutor)
        intentExplicito.putExtra("tituloLibro", titulo)
        startActivity(intentExplicito)
    }
    private fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }
}