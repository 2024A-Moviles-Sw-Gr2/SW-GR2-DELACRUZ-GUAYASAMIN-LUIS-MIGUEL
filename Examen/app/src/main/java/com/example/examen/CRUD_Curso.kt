package com.example.examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class CRUD_Curso : AppCompatActivity() {

    private var idCurso: Int = -1
    private lateinit var editTextMateria: EditText
    private lateinit var editTextNumeroEstudiantes: EditText
    private lateinit var editTextCodigo: EditText
    private lateinit var editTextActivo: EditText
    private lateinit var editTextDuracionHoras: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_curso)

        // Inicializa las vistas
        editTextMateria = findViewById(R.id.editTextMateria)
        editTextNumeroEstudiantes = findViewById(R.id.editTextNumeroEstudiantes)
        editTextCodigo = findViewById(R.id.editTextCodigo)
        editTextActivo = findViewById(R.id.editTextActivo)
        editTextDuracionHoras = findViewById(R.id.editTextDuracionHoras)
        btnGuardar = findViewById(R.id.btnGuardar)

        idCurso = intent.getIntExtra("ID_CURSO", -1)
        var titulo = findViewById<TextView>(R.id.tv_tituloC)

        if (idCurso != -1) {
            titulo.text = "Modificar Curso"
            // Si es un ID válido, estamos actualizando un curso existente
            cargarCurso(idCurso)
        }else {
            titulo.text = "Crear Curso"
        }

        btnGuardar.setOnClickListener {

            guardarCurso()
        }

        val btnCancelar: Button = findViewById(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            finish() // Regresa a la actividad anterior (MainActivity)
        }
    }

    private fun cargarCurso(id: Int) {
        val curso = ControladorBase.tablaCurso?.obtenerCursoPorId(id)
        curso?.let {
            editTextMateria.setText(it.materia)
            editTextNumeroEstudiantes.setText(it.numeroEstudiantes.toString())
            editTextCodigo.setText(it.codigo)
            editTextActivo.setText(if (it.activo) "1" else "0")
            editTextDuracionHoras.setText(it.duracionHoras.toString())
        }
    }

    private fun guardarCurso() {
        val materia = editTextMateria.text.toString()
        val numeroEstudiantes = editTextNumeroEstudiantes.text.toString().toIntOrNull() ?: 0
        val codigo = editTextCodigo.text.toString()
        val activo = editTextActivo.text.toString().toIntOrNull() == 1
        val duracionHoras = editTextDuracionHoras.text.toString().toDoubleOrNull() ?: 0.0

        val curso = Curso(
            id = idCurso,
            materia = materia,
            numeroEstudiantes = numeroEstudiantes,
            codigo = codigo,
            activo = activo,
            duracionHoras = duracionHoras
        )

        val exito = if (idCurso == -1) {
            ControladorBase.tablaCurso?.crearCurso(curso) ?: false
        } else {
            ControladorBase.tablaCurso?.actualizarCurso(curso) ?: false
        }

        if (exito) {
            setResult(RESULT_OK)
            Snackbar.make(findViewById(android.R.id.content), "Curso ${if (idCurso == -1) "creado" else "actualizado"}", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Error al guardar curso", Snackbar.LENGTH_SHORT).show()
        }
        finish() // Regresa a la actividad anterior (MainActivity)

    }
}