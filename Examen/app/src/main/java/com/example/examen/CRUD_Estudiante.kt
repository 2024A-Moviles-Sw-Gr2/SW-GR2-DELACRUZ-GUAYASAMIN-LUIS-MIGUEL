package com.example.examen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class CRUD_Estudiante : AppCompatActivity() {
    private var idEstudiante: Int = -1
    private lateinit var editTextNombre: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var editTextMatricula: EditText
    private lateinit var editTextFechaIngreso: EditText
    private lateinit var editTextPromedio: EditText
    private lateinit var editTextCursoId: EditText
    private lateinit var editTextLatitud: EditText
    private lateinit var editTextLongitud: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnSeleccionarUbicacion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_estudiante)

        // Inicialización de vistas
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextEdad = findViewById(R.id.editTextEdad)
        editTextMatricula = findViewById(R.id.editTextMatricula)
        editTextFechaIngreso = findViewById(R.id.editTextFechaIngreso)
        editTextPromedio = findViewById(R.id.editTextPromedio)
        editTextCursoId = findViewById(R.id.editTextCursoId)
        editTextLatitud = findViewById(R.id.editTextLatitud)
        editTextLongitud = findViewById(R.id.editTextLongitud)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnSeleccionarUbicacion = findViewById(R.id.btnSeleccionarUbicacion)

        // Obtener ID del estudiante y del curso
        idEstudiante = intent.getIntExtra("ID_ESTUDIANTE", -1)
        val idCurso = intent.getIntExtra("ID_CURSO", -1)
        val titulo = findViewById<TextView>(R.id.tv_tituloE)

        if (idEstudiante == -1) {
            titulo.text = "Crear Estudiante"
            editTextCursoId.setText(idCurso.toString())
            btnGuardar.setOnClickListener {
                crearEstudiante(idCurso)
            }
        } else {
            titulo.text = "Actualizar Estudiante"
            cargarDatosEstudiante()
            btnGuardar.setOnClickListener {
                actualizarEstudiante()
            }
        }

        btnSeleccionarUbicacion.setOnClickListener {
            val intent = Intent(this, SelecionMapa::class.java)
            startActivityForResult(intent, 1)
        }

        btnCancelar.setOnClickListener {
            finish() // Regresa a la actividad anterior (MainActivity o Estudiantes)
        }
    }

    private fun cargarDatosEstudiante() {
        val estudiante = ControladorBase.tablaEstudiante?.obtenerEstudiantePorId(idEstudiante)
        estudiante?.let {
            editTextNombre.setText(it.nombre)
            editTextEdad.setText(it.edad.toString())
            editTextMatricula.setText(it.matricula)
            editTextFechaIngreso.setText(it.fechaIngreso)
            editTextPromedio.setText(it.promedio.toString())
            editTextCursoId.setText(it.cursoId.toString())
            editTextLatitud.setText(it.latitud.toString())
            editTextLongitud.setText(it.longitud.toString())
        }
    }

    private fun crearEstudiante(idCurso: Int) {
        val estudiante = Estudiante(
            id = 0, // El ID se asignará automáticamente
            nombre = editTextNombre.text.toString(),
            edad = editTextEdad.text.toString().toInt(),
            matricula = editTextMatricula.text.toString(),
            fechaIngreso = editTextFechaIngreso.text.toString(),
            promedio = editTextPromedio.text.toString().toDouble(),
            cursoId = idCurso,
            latitud = editTextLatitud.text.toString().toDouble(),
            longitud = editTextLongitud.text.toString().toDouble()
        )
        if (ControladorBase.tablaEstudiante?.crearEstudiante(estudiante) == true) {
            Snackbar.make(findViewById(android.R.id.content), "Estudiante creado", Snackbar.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish() // Regresa a la actividad anterior (Estudiantes)
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Error al crear estudiante", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun actualizarEstudiante() {
        val estudiante = Estudiante(
            id = idEstudiante,
            nombre = editTextNombre.text.toString(),
            edad = editTextEdad.text.toString().toInt(),
            matricula = editTextMatricula.text.toString(),
            fechaIngreso = editTextFechaIngreso.text.toString(),
            promedio = editTextPromedio.text.toString().toDouble(),
            cursoId = editTextCursoId.text.toString().toInt(),
            latitud = editTextLatitud.text.toString().toDouble(),
            longitud = editTextLongitud.text.toString().toDouble()
        )
        if (ControladorBase.tablaEstudiante?.actualizarEstudiante(estudiante) == true) {
            Snackbar.make(findViewById(android.R.id.content), "Estudiante actualizado", Snackbar.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish() // Regresa a la actividad anterior (Estudiantes)
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Error al actualizar estudiante", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val latitudSeleccionada = data?.getDoubleExtra("latitud", 0.0)
            val longitudSeleccionada = data?.getDoubleExtra("longitud", 0.0)

            latitudSeleccionada?.let {
                editTextLatitud.setText(it.toString())
            }
            longitudSeleccionada?.let {
                editTextLongitud.setText(it.toString())
            }
        }
    }
}
