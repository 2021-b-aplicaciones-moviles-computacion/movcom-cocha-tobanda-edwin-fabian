package com.example.examen01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "examen01",
    null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaAutor =
            """
                CREATE TABLE AUTOR(ID_AUTOR INTEGER PRIMARY KEY AUTOINCREMENT,
                NOMBRE TEXT NOT NULL, 
                NACIONALIDAD TEXT NOT NULL,
                EDAD INTEGER NOT NULL)
            """.trimIndent()

        val scriptCrearTablaLibro =

            """
                CREATE TABLE LIBRO(ID_LIBRO INTEGER PRIMARY KEY AUTOINCREMENT,
                TITULO TEXT NOT NULL, 
                GENERO TEXT NOT NULL,
                ANIOPUBLI INTEGER NOT NULL, 
                DISPONIBLES INTEGER NOT NULL,
                PRECIO REAL NOT NULL,
                AUTOR INTEGER NOT NULL,
                FOREIGN KEY (AUTOR) REFERENCES AUTOR (ID_AUTOR) 
                )
            """.trimIndent()


        db?.execSQL(scriptCrearTablaAutor)
        Log.i("bdd", "Creación Tabla Autor")
        db?.execSQL(scriptCrearTablaLibro)
        Log.i("bdd", "Creación Tabla Libro")
    }


    fun crearAutor(
        nombre: String,
        nacionalidad: String,
        edad: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("NOMBRE", nombre)
        valoresAGuardar.put("NACIONALIDAD", nacionalidad)
        valoresAGuardar.put("EDAD", edad)

        val resultadoEscritura: Long = conexionEscritura.insert("AUTOR", null, valoresAGuardar)
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1

    }

    fun crearLibro(
        titulo: String,
        genero: String,
        anioPubli: Int,
        disponibles: Int,
        precio: Float,
        autor: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("TITULO", titulo)
        valoresAGuardar.put("GENERO", genero)
        valoresAGuardar.put("ANIOPUBLI", anioPubli)
        valoresAGuardar.put("DISPONIBLES", disponibles)
        valoresAGuardar.put("PRECIO", precio)
        valoresAGuardar.put("AUTOR", autor)

        val resultadoEscritura: Long = conexionEscritura.insert("LIBRO", null, valoresAGuardar)
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1

    }

    fun consultarAutorPorNombre(nombreAutor: String): Autor {
        val scriptConsultarAutor = "SELECT * FROM AUTOR WHERE NOMBRE=\"$nombreAutor\""
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarAutor, null)
        val existeAutor = resultadoConsultaLectura.moveToFirst()
        val autorEncontrado = Autor(0, "", "", 0)
        if (existeAutor) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val nacionalidad = resultadoConsultaLectura.getString(2)
                val edad = resultadoConsultaLectura.getInt(3)

                autorEncontrado.id = id
                autorEncontrado.nombre = nombre
                autorEncontrado.nacionalidad = nacionalidad
                autorEncontrado.edad = edad

            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return autorEncontrado
    }

    fun listarAutores(): ArrayList<Autor> {
        val scriptConsultarAutor = "SELECT * FROM AUTOR"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarAutor, null)
        val autores = ArrayList<Autor>()
        resultadoConsultaLectura.moveToFirst()
        while (!resultadoConsultaLectura.isAfterLast) {
            val autor = Autor(0, "", "", 0)
            autor.id = resultadoConsultaLectura.getInt(0)//ID
            autor.nombre = resultadoConsultaLectura.getString(1)
            autor.nacionalidad = resultadoConsultaLectura.getString(2)
            autor.edad = resultadoConsultaLectura.getInt(3)
            autores.add(autor)
            resultadoConsultaLectura.moveToNext()
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return autores
    }

    fun consultarLibrosAutor(idAutor:Int): ArrayList<Libro> {
        val scriptConsultarLibros = "SELECT * FROM LIBRO WHERE AUTOR=$idAutor"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarLibros, null)
        val libros = ArrayList<Libro>()
        resultadoConsultaLectura.moveToFirst()
        while (!resultadoConsultaLectura.isAfterLast) {
            val libro = Libro(0, "", "", 0, 0, 0.00f,0)
            libro.id = resultadoConsultaLectura.getInt(0)
            libro.titulo = resultadoConsultaLectura.getString(1)
            libro.genero = resultadoConsultaLectura.getString(2)
            libro.anioPubli = resultadoConsultaLectura.getInt(3)
            libro.disponibles = resultadoConsultaLectura.getInt(4)
            libro.precio = resultadoConsultaLectura.getFloat(5)
            libro.autor = resultadoConsultaLectura.getInt(6)
            libros.add(libro)
            resultadoConsultaLectura.moveToNext()
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return libros
    }

    fun consultarLibroPorTitulo(tituloLibro: String): Libro {
        val scriptConsultarLibro = "SELECT * FROM LIBRO WHERE TITULO=\"$tituloLibro\""
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarLibro, null)
        resultadoConsultaLectura.moveToFirst()
        val libro = Libro(0, "", "", 0, 0, 0.00f,0)
        while (!resultadoConsultaLectura.isAfterLast) {
            libro.id = resultadoConsultaLectura.getInt(0)
            libro.titulo = resultadoConsultaLectura.getString(1)
            libro.genero = resultadoConsultaLectura.getString(2)
            libro.anioPubli = resultadoConsultaLectura.getInt(3)
            libro.disponibles = resultadoConsultaLectura.getInt(4)
            libro.precio = resultadoConsultaLectura.getFloat(5)
            libro.autor = resultadoConsultaLectura.getInt(6)
            resultadoConsultaLectura.moveToNext()
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return libro
    }


    fun eliminarAutor(nombre: String): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion =
            conexionEscritura.delete("AUTOR", "NOMBRE=?", arrayOf(nombre))
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun eliminarLibro(titulo: String): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion =
            conexionEscritura.delete("LIBRO", "TITULO=?", arrayOf(titulo))
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarAutor(
        nombre: String,
        nacionalidad: String,
        edad: Int,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("NOMBRE", nombre)
        valoresAActualizar.put("NACIONALIDAD", nacionalidad)
        valoresAActualizar.put("EDAD", edad)
        val resultadoActualizacion = conexionEscritura.update(
            "AUTOR",
            valoresAActualizar,
            "ID_AUTOR=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun actualizarLibro(
        titulo: String,
        genero: String,
        anioPubli: Int,
        disponibles: Int,
        precio: Float,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("TITULO", titulo)
        valoresAActualizar.put("GENERO", genero)
        valoresAActualizar.put("ANIOPUBLI", anioPubli)
        valoresAActualizar.put("DISPONIBLES", disponibles)
        valoresAActualizar.put("PRECIO", precio)
        val resultadoActualizacion = conexionEscritura.update(
            "LIBRO",
            valoresAActualizar,
            "ID_LIBRO=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}


