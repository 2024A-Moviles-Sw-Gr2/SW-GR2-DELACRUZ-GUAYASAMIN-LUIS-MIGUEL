package com.example.examen

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private var idCursoSeleccionado: Int? = null

    companion object {
        private const val REQUEST_CODE_CREAR_CURSO = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAñadirCurso: View = findViewById(R.id.Btn_añadir_curso)
        btnAñadirCurso.setOnClickListener {
            val intent = Intent(this, CRUD_Curso::class.java).apply {
                putExtra("ID_CURSO", -1)
            }
            startActivityForResult(intent, REQUEST_CODE_CREAR_CURSO)
        }

        ControladorBase.tablaCurso = ESqliteHelperCurso(this)
        ControladorBase.tablaEstudiante = ESqliteHelperEstudiante(this)

        listView = findViewById(R.id.lista_materias)
        actualizarLista()

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val curso = listView.getItemAtPosition(info.position) as Curso
        idCursoSeleccionado = curso.id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_modificar -> {
                idCursoSeleccionado?.let {
                    val intent = Intent(this, CRUD_Curso::class.java).apply {
                        putExtra("ID_CURSO", it)
                    }
                    startActivityForResult(intent, REQUEST_CODE_CREAR_CURSO)
                }
                true
            }

            R.id.menu_eliminar -> {
                idCursoSeleccionado?.let {
                    abriDialogo(it)
                }
                true
            }

            R.id.menu_listar -> {
                idCursoSeleccionado?.let {
                    val intent = Intent(this, Estudiantes::class.java).apply {
                        putExtra("ID_CURSO", it)
                    }
                    startActivity(intent)
                }
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun abriDialogo(idCurso: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar el curso seleccionado?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            ControladorBase.tablaCurso?.eliminarCurso(idCurso)
            actualizarLista()
            Snackbar.make(
                findViewById(android.R.id.content),
                "Curso eliminado",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun actualizarLista() {
        val cursos = ControladorBase.tablaCurso?.obtenerTodosLosCursos() ?: emptyList()
        val adaptador = CustomAdapter(this, cursos)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREAR_CURSO && resultCode == RESULT_OK) {
            actualizarLista()
        }
    }
}
