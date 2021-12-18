import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun cargarLibros(): ArrayList<Libro> {
    val fileLibros: File?
    var frLibros: FileReader? = null
    val brLibros: BufferedReader?
    val listLibros: ArrayList<Libro> = ArrayList()

    try {
        fileLibros = File("libros.csv")
        frLibros = FileReader(fileLibros)
        brLibros = BufferedReader(frLibros)

        var row: String?
        while (brLibros.readLine().also { row = it } != null) {
            //declaro variables
            var auxRow = 0
            var auxFecha = 0

            var dayLibro = 0
            var yearLibro = 0
            var monthLibro = 0

            var libroID = ""
            var titulo = ""
            var tipo = ""
            var fechaPubli = Date(2000, 1, 1)
            var disponible = true//variable de tipo booleano, por defecto true
            var precio = 0.0
            var idAutor = 0

            val tokens = StringTokenizer(row, ",")//tokenizo la línea de entrada

            while (tokens.hasMoreTokens()) {//asigno los datos a las varibles correspondientes
                when (auxRow) {
                    0 -> {
                        libroID = tokens.nextToken()
                    }
                    1 -> {
                        titulo = tokens.nextToken()
                    }
                    2 -> {
                        tipo = tokens.nextToken()
                    }
                    3 -> {
                        val tokFecha = StringTokenizer(tokens.nextToken(), "/")
                        while (tokFecha.hasMoreTokens()) {
                            when (auxFecha) {
                                0 -> {

                                    yearLibro = tokFecha.nextToken().toInt()
                                }
                                1 -> {
                                    monthLibro = tokFecha.nextToken().toInt()
                                }
                                2 -> {
                                    dayLibro = tokFecha.nextToken().toInt()
                                }
                            }
                            auxFecha++
                        }
                        fechaPubli = Date(yearLibro, monthLibro, dayLibro)
                    }
                    4 -> {
                        val a = tokens.nextToken()
                        if (a == "true") {
                            disponible = true
                        } else if (a == "false") {
                            disponible = false
                        }
                    }
                    5->{
                        precio = tokens.nextToken().toDouble()
                    }
                    6 -> {
                        idAutor = tokens.nextToken().toInt()
                    }

                }
                auxRow++
            }
            listLibros.add(
                Libro(libroID, titulo, tipo, fechaPubli, disponible, precio, idAutor)
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            frLibros?.close()
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }
    return listLibros
}

fun registrarLibro(): Libro? {
    var idLibro: String? = ""
    var tituloLibro: String? = ""
    var tipoLibro: String? = ""
    val fechaPublic: String?
    var idAutor = 0
    var flag = true
    var fechaAux = 0
    var precio = 0.0

    var dayLibro = 0
    var monthLibro = 0
    var yearLibro = 0
    try {
        println("Ingrese el id del libro")
        idLibro = readLine() as String
        println("Ingrese el nombre del libro ")
        tituloLibro = readLine() as String
        println("Ingrese el género del libro")
        tipoLibro = readLine() as String
        println("Ingrese la fecha de publicacion del libro aaaa/mm/dd")
        fechaPublic = readLine()
        println("Ingrese el Precio del Libro")
        precio = readLine()?.toDouble() as Double
        println("Ingrese el ID del Autor")
        idAutor = readLine()?.toInt() as Int
        println("Por defecto el libro se registrará como \"DISPONIBLE\"")

        val tokFecha = StringTokenizer(fechaPublic, "/")
        while (tokFecha.hasMoreTokens()) {
            when (fechaAux) {
                0 -> {
                    yearLibro = tokFecha.nextToken().toInt()
                }
                1 -> {
                    monthLibro = tokFecha.nextToken().toInt()
                }
                2 -> {
                    dayLibro = tokFecha.nextToken().toInt()
                }
            }
            fechaAux++
        }

    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        return Libro(idLibro, tituloLibro, tipoLibro,
            Date(yearLibro, monthLibro, dayLibro),true, precio, idAutor )
    }
    return null
}
fun imprimirLibros(
    listaLibros: ArrayList<Libro>
) {
    listaLibros.forEach { libro ->
        println("Datos: $libro")
    }
}

fun actualizarLibros(
    libro: Libro?
): Libro? {
    var nombreLibro: String? = ""
    var tipoLibro: String? = ""
    val fechaPublic: String?
    val estadoLibro: String?
    var dispo = false
    var precio = 0.0
    var idAutor = 0

    var flag = true
    var fechaAux = 0

    var dayLibro = 0
    var monthLibro = 0
    var yearLibro = 0
    try {
        println("Ingrese el nuevo nombre del libro")
        nombreLibro = readLine() as String
        println("Ingrese el género del libro")
        tipoLibro = readLine() as String
        println("Ingrese la fecha de publicacion del libro aaaa/mm/dd")
        fechaPublic = readLine()

        val tokFecha = StringTokenizer(fechaPublic, "/")
        while (tokFecha.hasMoreTokens()) {
            when (fechaAux) {
                0 -> {
                    yearLibro = tokFecha.nextToken().toInt()
                }
                1 -> {
                    monthLibro = tokFecha.nextToken().toInt()
                }
                2 -> {
                    dayLibro = tokFecha.nextToken().toInt()
                }
            }
            fechaAux++
        }
        println(
            "¿Desea cambiar el estado del libro?\n" +
                    "Estado actual: ${libro?.disponible}\n" +
                    "Ingrese 1 si desea Activarlo\n" +
                    "Ingrese 0 si desea Desactivarlo"
        )
        estadoLibro = readLine()
        if (estadoLibro.equals("1")) {
            dispo = true
        }
        println("Ingrese el nuevo Precio del Libro")
        precio = readLine()?.toDouble() as Double
        println("Ingrese el nuevo ID del Autor del Libro")
        idAutor = readLine()?.toInt() as Int

    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        return Libro(libro?.idLibro, nombreLibro, tipoLibro,
            Date(yearLibro, monthLibro, dayLibro), dispo, precio,idAutor
        )
    }
    return null
}

fun guardarLibros(
    listaLibros: ArrayList<Libro>
) {
    var stringLibros = ""
    listaLibros.forEach { libro ->
        stringLibros = stringLibros + "" +
                "${libro.idLibro}," +
                "${libro.tituloLibro}," +
                "${libro.tipoLibro}," +
                "${libro.fechaPublic?.year}/${libro.fechaPublic?.month}/${libro.fechaPublic?.date}," +
                "${libro.disponible}," +
                "${libro.precio}," +
                "${libro.idAutor}\n"
    }
    var flwriter: FileWriter? = null
    try {
        flwriter =
            FileWriter("libros.csv")
        val bfwriter = BufferedWriter(flwriter)

        bfwriter.write(stringLibros)
        bfwriter.close()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (flwriter != null) {
            try {
                flwriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

class Libro(
    val idLibro: String?,
    val tituloLibro: String?,
    val tipoLibro: String?,
    val fechaPublic: Date?,
    val disponible: Boolean?,
    val precio: Double,
    val idAutor: Int

) {

    override fun toString(): String {
        return "\n\tId: \t\t$idLibro\n" +
                "\tNombre: \t$tituloLibro\n" +
                "\tGénero: \t$tipoLibro\n" +
                "\tFecha de Publicación:  ( ${fechaPublic?.year} / ${fechaPublic?.month} / ${fechaPublic?.date} )\n" +
                "\tDisponible: \t$disponible\n"+
                "\tPrecio (USD): \t$precio\n"+
                "\tId Autor: \t$idAutor"
    }
}
