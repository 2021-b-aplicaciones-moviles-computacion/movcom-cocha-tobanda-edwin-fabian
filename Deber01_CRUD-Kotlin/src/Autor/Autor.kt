import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun cargarAutores(): ArrayList<Autor> {

    val archivoAutores: File?
    var frAutores: FileReader? = null
    val brAutores: BufferedReader?
    val listaAutores: ArrayList<Autor> = ArrayList()

    try {
        archivoAutores = File("autores.csv")
        frAutores = FileReader(archivoAutores)
        brAutores = BufferedReader(frAutores)

        var linea: String?
        while (brAutores.readLine().also { linea = it } != null) {
            var auxLin = 0
            var idAutor = 0
            var nombreAutor = ""
            var paisAutor = ""
            var edadAutor = 0

            val tokens = StringTokenizer(linea, ",")

            while (tokens.hasMoreTokens()) {
                when (auxLin) {
                    0 -> {
                        idAutor = tokens.nextToken().toInt()
                    }
                    1 -> {
                        nombreAutor = tokens.nextToken()
                    }
                    2 -> {
                        paisAutor = tokens.nextToken()
                    }
                    3 -> {
                        edadAutor = tokens.nextToken().toInt()
                    }
                }
                auxLin++
            }
            listaAutores.add(
                Autor(idAutor, nombreAutor, paisAutor, edadAutor)
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            frAutores?.close()
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }
    return listaAutores
}

fun registrarAutor(): Autor? {
    var id = 0
    var nombre = ""
    var pais = ""
    var edad = 0
    var flag = true

    try {
        println("Ingrese el id del autor")
        id = readLine()?.toInt() as Int
        println("Ingrese el nombre del autor")
        nombre = readLine() as String
        println("Ingrese el pais de origen")
        pais = readLine() as String
        println("Ingrese la edad")
        edad = readLine()?.toInt()!!


    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        return Autor(id, nombre, pais, edad)
    }
    return null
}

fun imprimirAutores(listaAutores: ArrayList<Autor>) {
    listaAutores.forEach { autor ->
        println("Datos: $autor")
    }
}

fun actualizarAutor(
    autor: Autor?
): Autor? {
    var nombre = ""
    var pais = ""
    var edad = 0
    var flag = true
    try {
        println("Ingrese el nuevo nombre ")
        nombre = readLine() as String
        println("Ingrese el nuevo pais ")
        pais = readLine() as String
        println("Ingrese la nueva edad (0.0)")
        edad = readLine()?.toInt()!!


    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        if (autor != null) {
            return Autor(autor.idAutor, nombre, pais, edad)
        }
    }
    return null
}

fun guardarAutores(
    listaAutores: ArrayList<Autor>
) {

    var stringAutor = ""
    listaAutores.forEach { actor ->
        stringAutor = stringAutor + "" +
                "${actor.idAutor}," +
                "${actor.nombreAutor}," +
                "${actor.paisAutor}," +
                "${actor.edadActor}\n"
    }
    var fWriter: FileWriter? = null
    try {
        fWriter =
            FileWriter("autores.csv")
        val bfwriter = BufferedWriter(fWriter)

        bfwriter.write(stringAutor)
        bfwriter.close()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (fWriter != null) {
            try {
                fWriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

class Autor(
    val idAutor: Int,
    val nombreAutor: String,
    val paisAutor: String,
    val edadActor: Int,

    ) {
    override fun toString(): String {
        return "\n" +
                "\tId:\t$idAutor\n" +
                "\tNombre completo:  \t$nombreAutor\n" +
                "\tPaís:  \t$paisAutor\n" +
                "\tEdad:\t$edadActor\n"
    }
}