package Modelo

import com.google.gson.Gson
import java.io.File

class GestorCursos {
    private val gson = Gson()
    private val cursosFile = File("cursos.json")

    private var cursos: MutableList<Curso> = mutableListOf()


        init {
            if (cursosFile.exists()) {
                val cursosJson = cursosFile.readText()
                if (cursosJson.isNotEmpty()) {
                    cursos = gson.fromJson(cursosJson, Array<Curso>::class.java).toMutableList()
                }
            }
        }


    fun crearCurso(curso: Curso) {
        cursos.add(curso)
        guardarCursos()
    }

    fun eliminarCurso(index: Int) {
        if (index in 0 until cursos.size) {
            cursos.removeAt(index)
            guardarCursos()
        }
    }

    fun actualizarCurso(index: Int, curso: Curso) {
        if (index in 0 until cursos.size) {
            cursos[index] = curso
            guardarCursos()
        }
    }

    fun agregarEstudianteACurso(cursoIndex: Int, estudiante: Estudiante) {
        if (cursoIndex in 0 until cursos.size) {
            val curso = cursos[cursoIndex]
            curso.estudiantes.add(estudiante)
            guardarCursos()
        }
    }
    fun eliminarEstudianteDeCursos(nombre: String) {
        cursos.forEach { curso ->
            curso.estudiantes.removeIf { it.nombre == nombre }
        }
        guardarCursos()
    }


    fun actualizarAlumno(nombre: String, estudianteAct: Estudiante) {
        for (curso in cursos) {
            val estudiantes = curso.estudiantes
            if (estudiantes != null) {
                for (estudiante in estudiantes) {
                    if (estudiante.nombre == nombre) {
                        estudiante.nombre = estudianteAct.nombre
                        estudiante.edad = estudianteAct.edad
                        estudiante.matricula = estudianteAct.matricula
                        estudiante.fechaIngreso = estudianteAct.fechaIngreso
                        estudiante.promedio = estudianteAct.promedio
                    }
                }
            }
        }
        guardarCursos()
    }

    private fun guardarCursos() {
        val cursosJson = gson.toJson(cursos)
        cursosFile.writeText(cursosJson)
    }

    fun leerCursos(): List<Curso> {
        return cursos.toList()
    }
}
