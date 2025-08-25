// ActivityOne.kt
package edu.unicatolica.kotlin

fun main() {

    print("Escriba el numero de estudiantes del curso:")
    val n = readln().toInt()

    val resgisro = mutableListOf<Pair<Int, Double>>()


    for (i in 1..n) {
        var nota: Double

        while (true) {
            println("Escriba la nota del estudiante # $i ,solo valores entre  (0.0 - 5.0) ")
            val nota = readln().toDouble()

            if (nota in 0.0..5.0) {
                println("la nota es valida: $nota ")
                resgisro.add(i to nota)
                break

            } else {
                println("la nota no es valida, ingresala nuevamente ")
            }
        }
    }
    println(" \n registro completado: ")
    for ((i, nota) in resgisro) {
        println("estudiante:  $i -> nota : $nota")

    }

        while (true) {
            println("\nSeleccione una opcion:")
            println("1. Promedio general del grupo")
            println("2. Estudiantes aprobados y reprobados")
            println("3. Nota mas alta y más baja")
            println("4. Desempeño de cada estudiante")
            println("5. Salir")

            println("\nescriba la opcion a ejecutar:")
            val opcion = readln().toInt()

            when (opcion) {
                1-> {
                    val promedio = resgisro.map {it.second}.average()
                    println("el promedio del grupo es: $promedio")
                }
                2 -> {
                    val aprobados= resgisro.filter { it.second >= 3.0 }
                    val reprobados= resgisro.filter { it.second < 3.0 }
                    println ("los estudiantes aprobados son: estudiante #${aprobados.map { it.first }}")
                    println ("los estudiantes reprobados son: estudiante #${reprobados.map{it.first}}")
                }
                3 -> {
                    val maxNota = resgisro.maxBy { it.second }
                    val minNota = resgisro.minBy { it.second }
                    println ("La nota mas alta : estudiante ${maxNota.first} con ${maxNota.second}")
                    println ("La nota mas baja : estudiante ${minNota.first} con ${minNota.second}")

                }
                4 ->{
                    for ((i, nota) in resgisro){
                        val desempeño = when {
                            nota >= 4.5 -> "Excelente"
                            nota >= 4.0 -> "Muy bien"
                            nota >= 3.0 -> "Aprobado"
                            else -> "Reprobado"
                        }
                        println ("estudiante $i -> Nota $nota : $desempeño")
                    }
                }
                5 ->{
                    println(" Gracias por usar nuestro sistema \n saliendo... ")
                    break
                }
                else -> println("opcion no valida ")

            }
        }
    }


    /*
 Actividad Semana 2: Calculadora de estadísticas de calificaciones

 Enunciado:
 El programa debe solicitar al usuario la cantidad de estudiantes (N) y, a continuación,
 leer la nota de cada uno (valor entre 0.0 y 5.0). Con esta información se debe calcular y mostrar:

 1. El promedio general del grupo.
 2. La cantidad de estudiantes aprobados (nota >= 3.0) y reprobados (nota < 3.0).
 3. La nota más alta y la nota más baja obtenida.
 4. Una clasificación del promedio general según la siguiente escala:
    - Promedio >= 4.5  → "Excelente"
    - Promedio >= 4.0  → "Muy bien"
    - Promedio >= 3.0  → "Aprobado"
    - Promedio < 3.0   → "Reprobado"

 Requisitos:
 - Validar que cada nota ingresada esté en el rango permitido (0.0 a 5.0).
 - Si el usuario ingresa un valor inválido, volver a solicitar la nota.
 - Usar condicionales para la clasificación y bucles para recorrer las notas.
 */


