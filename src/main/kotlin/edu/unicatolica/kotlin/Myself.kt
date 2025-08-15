package edu.unicatolica.kotlin

fun main() {
    val firstName: String = "Kevin Fernando"
    val lastName = "Serna Goyes"
    val program = "tecnologia en desarrollo de software"
    val semester = "6to"
    val id = "406954"
    val interest = "Mi principal interes en este momento es el desarrollo de agentes automatizados, " +
            "ya sea mediante integraciones via API o a traves de flujos de trabajo utilizando herramientas como n8n.\n" +
            "Me encuentro en una etapa activa de aprendizaje en esta area, con gran motivacion por seguir profundizando en su aplicacion en distintos contextos."

    val message = "Mi nombre es: $firstName $lastName, me encuentro en $semester del programa $program con el ID: $id\n\n$interest"

    println(message)
}
