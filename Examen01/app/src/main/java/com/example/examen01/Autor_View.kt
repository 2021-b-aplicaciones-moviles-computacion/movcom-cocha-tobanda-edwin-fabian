package com.example.examen01

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class Autor_View : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var nombreAutorSeleccionado = ""
    var idAutorSeleccionado = 0
    lateinit var autores :ArrayList<Autor>
    lateinit var adaptador: ArrayAdapter<Autor>
    lateinit var listAutoresView: ListView

    override fun onStart() {
        super.onStart()
        BaseDatos.Tablas = SQLiteHelper(this)
        autores = BaseDatos.Tablas!!.listarAutores()
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, autores)
        listAutoresView = findViewById(R.id.listView_Autor)
        adaptador.notifyDataSetChanged()
        listAutoresView.adapter = adaptador
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor_view)
        BaseDatos.Tablas = SQLiteHelper(this)
        onStart()
        val botonCrearAutor = findViewById<Button>(R.id.btn_crear_Autor)
        val botonVerLibros = findViewById<Button>(R.id.btn_verLibros)
        botonVerLibros.isEnabled = false //Desactivo botón hasta seleccionar
        botonCrearAutor.setOnClickListener { abrirActividad(CrearAutor::class.java) }
        registerForContextMenu(listAutoresView)
        val infoAutor = findViewById<TextView>(R.id.txt_InfoAutor)

        listAutoresView.setOnItemClickListener { _, _, position, _ ->
            botonVerLibros.isEnabled = true
            val dirSelec = listAutoresView.getItemAtPosition(position) as Autor
            infoAutor.text = dirSelec.imprimirDatosAutor()
            botonVerLibros.setOnClickListener {
                abrirActividadConParametros(
                    dirSelec.nombre,
                    dirSelec.id,
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

        BaseDatos.Tablas = SQLiteHelper(this)
        onStart()
        val autorSeleccionado = listAutoresView.getItemAtPosition(posicionItemSeleccionado) as Autor
        nombreAutorSeleccionado = autorSeleccionado.nombre
        idAutorSeleccionado = autorSeleccionado.id
        listAutoresView.adapter = adaptador
        val cancelarClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_SHORT).show()
        }
        val eliminarClick = { _: DialogInterface, _: Int ->
            Log.i("bdd", "Nombre Autor: $nombreAutorSeleccionado")
            BaseDatos.Tablas!!.eliminarAutor(nombreAutorSeleccionado)
            onStart()
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
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

    fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }

    private fun abrirActividadConParametros(autor: String, idAutor: Int, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreAutor", autor)
        intentExplicito.putExtra("idAutor", idAutor)
        startActivity(intentExplicito)
    }


}