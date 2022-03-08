package com.example.examen02

class Libro(
    var id: String?=null,
    var titulo: String?=null,
    var genero: String?=null,
    var anioPubli: Int?=null,
    var disponibles: Int?=null,
    var precio: Float?=null,
    var autor: String?=null
) {

    override fun toString(): String {
        return titulo!!
    }

    fun imprimirDatosLibro(): String {
        return """
                Título: $titulo 
                Género: $genero
                Año de publicación: $anioPubli
                Disponibles: $disponibles
                Precio: $precio USD
                
            """.trimIndent()
    }
}