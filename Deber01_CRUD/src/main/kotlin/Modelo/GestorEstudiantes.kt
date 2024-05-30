package Modelo
import Modelo.Estudiante
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class GestorEstudiantes {
    private val estudiantes = mutableListOf<Estudiante>()
    private val gson = Gson()
    private val estudiantesFile = File("estudiantes.json")

    init {
        if (estudiantesFile.exists()) {
            val estudianteListType = object : TypeToken<List<Estudiante>>() {}.type
            val estudiantesFromFile: List<Estudiante> = gson.fromJson(estudiantesFile.readText(), estudianteListType)
            estudiantes.addAll(estudiantesFromFile)
        }
    }

    fun crearEstudiante(estudiante: Estudiante) {
        estudiantes.add(estudiante)
        guardarEstudiantes()
    }

    fun leerEstudiantes(): List<Estudiante> {
        return estudiantes
    }

    fun eliminarEstudiante(index: Int) {
        estudiantes.removeAt(index)
        guardarEstudiantes()
    }

    fun actualizarEstudiante(index: Int, estudianteActualizado: Estudiante) {
        val estudiante = estudiantes.getOrNull(index)
        if (estudiante != null) {
            estudiante.nombre = estudianteActualizado.nombre
            estudiante.edad = estudianteActualizado.edad
            estudiante.matricula = estudianteActualizado.matricula
            estudiante.fechaIngreso = estudianteActualizado.fechaIngreso
            estudiante.promedio = estudianteActualizado.promedio
            guardarEstudiantes()
        }
    }

    private fun guardarEstudiantes() {
        estudiantesFile.writeText(gson.toJson(estudiantes))
    }
}
