package com.example.examen01

class Libro(
    var id: Int,
    var titulo: String,
    var genero: String,
    var anioPubli: Int,
    var disponibles: Int,
    var precio: Float,
    var autor: Int
) {

    override fun toString(): String {
        return titulo
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