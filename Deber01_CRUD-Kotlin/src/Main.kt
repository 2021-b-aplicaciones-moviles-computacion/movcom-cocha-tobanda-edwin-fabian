import kotlin.collections.ArrayList
import kotlin.system.exitProcess

fun main() {

    //Creacion de listas y carga inicial
    val listaLibros: ArrayList<Libro> = cargarLibros()
    val listaAutores: ArrayList<Autor> = cargarAutores()

    var opcion:Int
    do {
        println("\n******************LIBROS Y AUTORES*******************")
        println("Seleccione una opción: ")
        println(
            "1- Ingresar una nuevo Libro/Autor\n" +
                    "2- Mostrar Libros/Autores\n" +
                    "3- Actualizar Libro/Autor \n" +
                    "4- Eliminar Libro/Autor\n" +
                    "5- Salir del Sistema."
        )
        try {
            opcion = readLine()?.toInt() as Int
            when (opcion) {
                1 -> {
                    crear(listaLibros, listaAutores)
                    guardarLibros(listaLibros)
                    guardarAutores(listaAutores)
                }
                2 -> {
                    leer(listaLibros, listaAutores)
                }
                3 -> {
                    actualizar(listaLibros, listaAutores)
                    guardarAutores(listaAutores)
                    guardarLibros(listaLibros)
                }
                4 -> {
                    borrar(listaLibros, listaAutores)
                    guardarAutores(listaAutores)
                    guardarLibros(listaLibros)
                }
                5 -> {
                    exitProcess(0)
                }
                else -> imprimirError(0)
            }

        } catch (e2: Exception) {
            imprimirError(1)
        }
    } while (true)
}

fun imprimirExito(opcion: Int = 0) {
    val listaLibros: ArrayList<Libro> = cargarLibros()
    val listaAutores: ArrayList<Autor> = cargarAutores()
    when (opcion) {
        0 -> {
            println(
                "****************************\n" +
                        "Registro ingresado exitosamente\n" +
                        "****************************"
            )
            println("Presione una tecla para volver al Menú Principal")

        }
        1 -> {
            println(
                "****************\n" +
                        "Consulta exitosa\n" +
                        "****************"
            )
            println("Presione una tecla para volver al Menú Principal")
        }
        2 -> {
            println(
                "**********************\n" +
                        "Registro actualizado exitosamente\n" +
                        "**********************"
            )
            println("Presione una tecla para volver al Menú Principal")
        }
        else -> {
            println(
                "******************\n" +
                        "Error desconocido\n" +
                        "******************"
            )
            println("Presione una tecla para volver al Menú Principal")
        }
    }

    readLine()
}

fun imprimirError(opcion: Int = 0) {
    when (opcion) {
        0 -> {
            println(
                "********************\n" +
                        "Opción no disponible\n" +
                        "********************"
            )
            println("Presione una tecla para volver al Menú Principal")
        }
        1 -> {
            println(
                "**********************************************************************************\n" +
                        "La opcion ingresada no es correcta, revise si ingresó un caracter no numérico\n" +
                        "**********************************************************************************"
            )
            println("Presione una tecla para volver al Menú Principal")
        }
        2 -> {
            println(
                "***********************\n" +
                        "Elemento no encontrado\n" +
                        "***********************#"
            )
            println("Presione una tecla para volver al Menú Principal")
        }
        3 -> {
            println(
                "*******************\n" +
                        "Operación cancelada\n" +
                        "*******************"
            )
            println("Presione una tecla para volver al Menú Principal")
        }
        else -> {
            println(
                "*****************\n" +
                        "Error desconocido\n" +
                        "*****************"
            )
            println("Presione una tecla para volver al Menú Principal")
        }
    }
    readLine()
}

fun buscarLibroID(
    listaLibros: ArrayList<Libro>,
    idLibro: String?
): Libro? {
    var libroRespuesta: Libro? = null
    listaLibros.forEach { serie ->
        if (serie.idLibro.equals(idLibro)) {
            libroRespuesta = serie
        }
    }
    return libroRespuesta
}

fun buscarAutorID(
    listaAutores: ArrayList<Autor>,
    idAutor: Int
): Autor? {
    var autorRespuesta: Autor? = null
    listaAutores.forEach { autor ->
        if (autor.idAutor == idAutor) {
            autorRespuesta = autor
        }
    }
    return autorRespuesta
}
