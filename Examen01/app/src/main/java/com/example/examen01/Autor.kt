package com.example.examen01

class Autor(
    var id: Int,
    var nombre: String,
    var nacionalidad: String,
    var edad: Int)
{
    override fun toString(): String {
        return nombre
    }

    fun imprimirDatosAutor(): String {
        return """
                Nombre: $nombre 
                Nacionalidad: $nacionalidad
                Edad: $edad
            """.trimIndent()
    }

}