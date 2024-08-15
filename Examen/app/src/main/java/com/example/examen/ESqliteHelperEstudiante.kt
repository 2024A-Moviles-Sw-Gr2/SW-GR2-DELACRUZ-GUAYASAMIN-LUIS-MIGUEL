package com.example.examen

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEstudiante(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "Examen",
    null,
    1 // Versión inicial de la base de datos
) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Crear tabla ESTUDIANTE
        val scriptSQLCrearTablaEstudiante = """
            CREATE TABLE ESTUDIANTE (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                edad INTEGER,
                matricula VARCHAR(50),
                fechaIngreso VARCHAR(50),
                promedio DOUBLE,
                cursoId INTEGER,
                latitud DOUBLE,
                longitud DOUBLE,
                FOREIGN KEY (cursoId) REFERENCES CURSO(id)
            )
        """.trimIndent()

        db?.execSQL(scriptSQLCrearTablaEstudiante)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            db?.execSQL("DROP TABLE IF EXISTS ESTUDIANTE")
            onCreate(db)
        }
    }

    // Métodos CRUD para Estudiante

    fun crearEstudiante(estudiante: Estudiante): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombre", estudiante.nombre)
            put("edad", estudiante.edad)
            put("matricula", estudiante.matricula)
            put("fechaIngreso", estudiante.fechaIngreso)
            put("promedio", estudiante.promedio)
            put("cursoId", estudiante.cursoId)
            put("latitud", estudiante.latitud)
            put("longitud", estudiante.longitud)
        }
        val resultadoGuardar = baseDatosEscritura.insert("ESTUDIANTE", null, valoresAGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarEstudiante(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura.delete("ESTUDIANTE", "id=?", arrayOf(id.toString()))
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarEstudiante(estudiante: Estudiante): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", estudiante.nombre)
            put("edad", estudiante.edad)
            put("matricula", estudiante.matricula)
            put("fechaIngreso", estudiante.fechaIngreso)
            put("promedio", estudiante.promedio)
            put("cursoId", estudiante.cursoId)
            put("latitud", estudiante.latitud)
            put("longitud", estudiante.longitud)
        }
        val resultadoActualizacion = conexionEscritura.update("ESTUDIANTE", valoresAActualizar, "id=?", arrayOf(estudiante.id.toString()))
        conexionEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }

    fun obtenerEstudiantesDeCurso(cursoId: Int): List<Estudiante> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT *
            FROM ESTUDIANTE 
            WHERE cursoId = ?
        """.trimIndent()
        val arregloParametrosConsultaLectura = arrayOf(cursoId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, arregloParametrosConsultaLectura)

        val estudiantes = mutableListOf<Estudiante>()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val estudiante = Estudiante(
                    id = resultadoConsultaLectura.getInt(0),
                    nombre = resultadoConsultaLectura.getString(1),
                    edad = resultadoConsultaLectura.getInt(2),
                    matricula = resultadoConsultaLectura.getString(3),
                    fechaIngreso = resultadoConsultaLectura.getString(4),
                    promedio = resultadoConsultaLectura.getDouble(5),
                    cursoId = resultadoConsultaLectura.getInt(6),
                    latitud = resultadoConsultaLectura.getDouble(7),
                    longitud = resultadoConsultaLectura.getDouble(8)
                )
                estudiantes.add(estudiante)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return estudiantes
    }

    fun obtenerEstudiantePorId(id: Int): Estudiante? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM ESTUDIANTE WHERE id = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, arrayOf(id.toString()))
        var estudiante: Estudiante? = null
        if (resultadoConsultaLectura.moveToFirst()) {
            estudiante = Estudiante(
                id = resultadoConsultaLectura.getInt(0),
                nombre = resultadoConsultaLectura.getString(1),
                edad = resultadoConsultaLectura.getInt(2),
                matricula = resultadoConsultaLectura.getString(3),
                fechaIngreso = resultadoConsultaLectura.getString(4),
                promedio = resultadoConsultaLectura.getDouble(5),
                cursoId = resultadoConsultaLectura.getInt(6),
                latitud = resultadoConsultaLectura.getDouble(7),
                longitud = resultadoConsultaLectura.getDouble(8)
            )
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return estudiante
    }
}
