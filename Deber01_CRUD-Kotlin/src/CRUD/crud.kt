fun crear(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>

){
    println(
        "Desea Ingresar: " +
                "\n1- Libros" +
                "\n2- Autores"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                crearUno(listaLibros, listaAutores)
            }
            2 -> {
                crearDos(listaAutores)
            }
            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun crearUno(
    listaLibros: ArrayList<Libro>, listaAutores: ArrayList<Autor>
){
    val libroAux: Libro? = registrarLibro()
    if (libroAux != null) {
        if(buscarAutorID(listaAutores,libroAux.idAutor)==null ) {
            println("No existe un Autor registrado con ese ID, considere primero Ingresar dicho Autor")
            println("Presione una tecla para volver al Menú Principal")
            return readLine() as Unit
        }
        if (buscarLibroID(listaLibros, libroAux.idLibro) != null){
            println("Ya existe un registro con ese ID de libro, pruebe a Actualizar en el menú principal")
            println("Presione una tecla para volver al Menú Principal")
            return readLine() as Unit
        }
        listaLibros.add(libroAux)
        imprimirExito(0)
    }
}

fun crearDos(
    listaAutores: ArrayList<Autor>
){
    val autorAux: Autor? = registrarAutor()
    if (autorAux != null) {
        if(buscarAutorID(listaAutores,autorAux.idAutor)!=null ) {
            println("Ya existe un registro con ese ID, pruebe a Actualizar en el menú principal")
            println("Presione una tecla para volver al Menú Principal")
            return readLine() as Unit
        }
        listaAutores.add(autorAux)
        imprimirExito(0)
    }
}

                                //********DELETE******************
fun borrar(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>
){
    println(
        "Desea Eliminar: " +
                "\n1- Libros" +
                "\n2- Autores"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                eliminarUno(listaLibros, listaAutores)
            }
            2 -> {
                eliminarDos(listaAutores)
            }
            else -> imprimirError(0)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun eliminarUno(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>
){
    println("Ingrese el id del libro a eliminar:\n")
    try {
        val ingreso = readLine() as String
        val libroID: Libro? = buscarLibroID(listaLibros, ingreso)
        var confirmacion = 0
        if (libroID != null) {
            println("Informacion del libro a eliminar:")
            println(libroID)
            try {
                println(
                    "¿Está seguro de eliminar del libro?\n" +
                            "Ingrese 1 si está seguro\n" +
                            "Ingrese 0 si no desea eliminarlo"
                )
                confirmacion = readLine()?.toInt() as Int
                if (confirmacion == 1) {
                    listaLibros.removeIf { libro ->
                        (libro.idLibro.equals(ingreso))
                    }
                } else {
                    imprimirError(3)
                }
            } catch (err: Exception) {
                imprimirError(1)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun eliminarDos(
    listaAutores: ArrayList<Autor>,
){
    println("Ingrese el id del autor que desea eliminar\n")
    try {
        val entrada = readLine()?.toInt() as Int
        val actorID: Autor? = buscarAutorID(listaAutores, entrada)
        val seguro: String?
        if (actorID != null) {
            println("Información actual del autor:")
            println(actorID)
            try {
                println(
                    "¿Está seguro de eliminar el autor?\n" +
                            "Ingrese 1 si está seguro\n" +
                            "Ingrese 0 si no desea eliminarlo"
                )
                seguro = readLine() as String
                if (seguro =="1") {
                    listaAutores.removeIf { autor ->
                        (autor.idAutor == entrada)
                    }
                } else {
                    imprimirError(3)
                }
            } catch (err: Exception) {
                imprimirError(1)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

//*************************UPDATE*******************

fun actualizar(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>
){
    println(
        "Desea actualizar: " +
                "\n1- Libros" +
                "\n2- Autores"
    )
    try {

        when (readLine()?.toInt() as Int) {
            1 -> {
                actualizarUno(listaLibros)
            }
            2 -> {
                actualizarDos(listaAutores)
            }
            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun actualizarUno(
    listaLibros: ArrayList<Libro>,
){
    println("Ingrese el id del libro a actualizar:\n")
    try {
        val entrada = readLine() as String
        val libroID: Libro? = buscarLibroID(listaLibros, entrada)
        val updateLibro: Libro?
        if (libroID != null) {
            println("Información actual del libro:")
            println(libroID)
            updateLibro = actualizarLibros(libroID)
            listaLibros.removeIf { libro ->
                (libro.idLibro.equals(entrada))
            }
            if (updateLibro != null) {
                listaLibros.add(updateLibro)
                println("Informacion Actualizada:\n" + "+${updateLibro}")
                    imprimirExito(2)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun actualizarDos(
    listaAutores: ArrayList<Autor>,

    ){
    println("Ingrese el id del autor que desea actualizar\n")
    try {

        val autor = readLine()?.toInt() as Int
        val autorID: Autor? = buscarAutorID(listaAutores, autor)
        val updateAutor: Autor?
        if (autorID != null) {
            println("Información actual del autor:")
            println(autorID)
            updateAutor = actualizarAutor(autorID)
            listaAutores.removeIf { actor1 ->
                (actor1.idAutor == autor)
            }
            if (updateAutor != null) {
                listaAutores.add(updateAutor)
                println("Informacion actualizada:\n" + "+${updateAutor}")
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

//*********************SEARCH***************

fun leer(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>
){
    println(
        "¿Qué desea ver?: " +
                "\n1- Libros" +
                "\n2- Autores"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionR1(listaLibros)
            }
            2 -> {
                opcionR2(listaAutores)
            }
            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionR1(
    listaLibros: ArrayList<Libro>
){
    println(
        "Seleccione una Opción: " +
                "\n1- Listar TODOS los Libros" +
                "\n2- Buscar por ID del Libro"+
                "\n3- Buscar por ID de Autor"
    )
    try{
        when (readLine()?.toInt() as Int){
            1 -> {
                println("Lista Libros")
                imprimirLibros(listaLibros)
                imprimirExito(1)
            }
            2 ->{
                println("Ingrese el ID del Libro a buscar")
                var libroB = buscarLibroID(listaLibros, readLine() as String)
                if(libroB==null){
                    imprimirError(2)
                }
                println("Datos del Libro: $libroB")
            }
            3->{
                println("Ingrese el ID del Autor")
                var idAutor = readLine()?.toInt() as Int
                var libroBA: Libro? = null
                listaLibros.forEach { libro ->
                    if (libro.idAutor == idAutor) {
                        libroBA = libro
                        println("Datos del Libro: $libroBA")
                    }
                }
                if(libroBA == null){
                    imprimirError(2)
                }
            }
        }
    }catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionR2(
    listaAutores: ArrayList<Autor>
){
    println(
        "Seleccione una Opción: " +
                "\n1- Listar TODOS los Autores" +
                "\n2- Buscar por ID de Autor"
    )
    try{
        when (readLine()?.toInt() as Int){
            1->{
                println("Lista Autores")
                imprimirAutores(listaAutores)
            }
            2 ->{
                println("Ingrese el ID del Autor a buscar")
                var autorB = buscarAutorID(listaAutores, readLine()?.toInt() as Int)
                if(autorB==null){
                    imprimirError(2)
                }
                println("Datos del Autor: $autorB")
            }
        }
    }catch (err: Exception) {
        imprimirError(1)
    }
}
