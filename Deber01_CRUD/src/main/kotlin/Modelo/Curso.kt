package Modelo

data class Curso(
    var materia: String,
    var numeroEstudiantes: Int,
    var codigo: String,
    var activo: Boolean,
    var duracionHoras: Double,
    var estudiantes: MutableSet<Estudiante> = mutableSetOf()
)
