package com.example.deber02_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class Estudiantes : AppCompatActivity() {

    private lateinit var listView: ListView
    private var idEstudianteSeleccionado: Int? = null
    private var idCurso: Int? = null

    companion object {
        private const val REQUEST_CODE_CRUD_ESTUDIANTE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes)

        idCurso = intent.getIntExtra("ID_CURSO", -1)

        listView = findViewById(R.id.list_estudiantes)
        actualizarLista()

        registerForContextMenu(listView)

        val btnAñadirEstudiante: View = findViewById(R.id.Btn_añadir_Estudiante)
        btnAñadirEstudiante.setOnClickListener {
            val intent = Intent(this, CRUD_Estudiante::class.java).apply {
                putExtra("ID_CURSO", idCurso)
            }
            startActivityForResult(intent, REQUEST_CODE_CRUD_ESTUDIANTE)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_estudiante, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val estudiante = listView.getItemAtPosition(info.position) as Estudiante
        idEstudianteSeleccionado = estudiante.id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.modificar_estudiante -> {
                idEstudianteSeleccionado?.let {
                    val intent = Intent(this, CRUD_Estudiante::class.java).apply {
                        putExtra("ID_ESTUDIANTE", it)
                        putExtra("ID_CURSO", idCurso)
                    }
                    startActivityForResult(intent, REQUEST_CODE_CRUD_ESTUDIANTE)
                }
                true
            }
            R.id.eliminar_estudiante -> {
                idEstudianteSeleccionado?.let {
                    abrirDialogo(it)
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun abrirDialogo(idEstudiante: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar el estudiante seleccionado?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            ControladorBase.tablaEstudiante?.eliminarEstudiante(idEstudiante)
            actualizarLista()
            Snackbar.make(findViewById(android.R.id.content), "Estudiante eliminado", Snackbar.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun actualizarLista() {
        val estudiantes = ControladorBase.tablaEstudiante?.obtenerEstudiantesDeCurso(idCurso ?: -1) ?: emptyList()
        val adaptador = EstudianteAdapter(this, estudiantes)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CRUD_ESTUDIANTE && resultCode == RESULT_OK) {
            actualizarLista()
        }
    }
}
