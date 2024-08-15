package com.example.examen

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperCurso(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "examen",
    null,
    2
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCurso = """
            CREATE TABLE CURSO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                materia VARCHAR(50),
                numeroEstudiantes INTEGER,
                codigo VARCHAR(50),
                activo INTEGER,
                duracionHoras DOUBLE
            )
        """.trimIndent()

        db?.execSQL(scriptSQLCrearTablaCurso)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("DROP TABLE IF EXISTS CURSO")
            onCreate(db)
        }
    }

    // Métodos CRUD para Curso

    fun crearCurso(curso: Curso): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("materia", curso.materia)
            put("numeroEstudiantes", curso.numeroEstudiantes)
            put("codigo", curso.codigo)
            put("activo", if (curso.activo) 1 else 0)
            put("duracionHoras", curso.duracionHoras)
        }
        val resultadoGuardar = baseDatosEscritura.insert("CURSO", null, valoresAGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarCurso(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura.delete("CURSO", "id=?", arrayOf(id.toString()))
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarCurso(curso: Curso): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("materia", curso.materia)
            put("numeroEstudiantes", curso.numeroEstudiantes)
            put("codigo", curso.codigo)
            put("activo", if (curso.activo) 1 else 0)
            put("duracionHoras", curso.duracionHoras)
        }
        val resultadoActualizacion = conexionEscritura.update(
            "CURSO",
            valoresAActualizar,
            "id=?",
            arrayOf(curso.id.toString())
        )
        conexionEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }

    fun obtenerTodosLosCursos(): MutableList<Curso>? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CURSO"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        val cursos = mutableListOf<Curso>()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val curso = Curso(
                    id = resultadoConsultaLectura.getInt(0),
                    materia = resultadoConsultaLectura.getString(1),
                    numeroEstudiantes = resultadoConsultaLectura.getInt(2),
                    codigo = resultadoConsultaLectura.getString(3),
                    activo = resultadoConsultaLectura.getInt(4) == 1,
                    duracionHoras = resultadoConsultaLectura.getDouble(5)
                )
                cursos.add(curso)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return cursos
    }
    fun obtenerCursoPorId(id: Int): Curso? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM CURSO WHERE id = ?"
        val cursor = baseDatosLectura.rawQuery(scriptConsultaLectura, arrayOf(id.toString()))

        val curso: Curso? = if (cursor.moveToFirst()) {
            Curso(
                id = cursor.getInt(0),
                materia = cursor.getString(1),
                numeroEstudiantes = cursor.getInt(2),
                codigo = cursor.getString(3),
                activo = cursor.getInt(4) == 1,
                duracionHoras = cursor.getDouble(5)
            )
        } else {
            null
        }

        cursor.close()
        baseDatosLectura.close()
        return curso
    }

}