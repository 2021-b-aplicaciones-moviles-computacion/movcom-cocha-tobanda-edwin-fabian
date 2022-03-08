package com.example.examen02

class Autor(
            var id: String?=null,
            var nombre: String?=null,
            var nacionalidad: String?=null,
            var edad: Int?=null) {


    override fun toString(): String {
        return nombre!!
    }

    fun imprimirDatosAutor(): String {
        return """
                Nombre: $nombre 
                Nacionalidad: $nacionalidad
                Edad: $edad
            """.trimIndent()
    }

}