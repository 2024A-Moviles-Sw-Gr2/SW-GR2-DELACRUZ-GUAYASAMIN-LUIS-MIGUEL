package com.example.a2024aswgr2_luisdlc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)
        // Buscar entrenador
        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val entrenador = EbaseDeDatos.tablaEntrenador!!
                .consultarEntrenadorPorID(
                    id.text.toString().toInt()
                )

            if(entrenador == null){
                mostrarSnackbar("Usuario no encontrado")
                id.setText("")
                nombre.setText("")
                descripcion.setText("")
            } else {
                id.setText(entrenador.id.toString())
                nombre.setText(entrenador.nombre)
                descripcion.setText(entrenador.descripcion)
                mostrarSnackbar("Usuario encontrado")
            }
        }

        // Crear entrenador
        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EbaseDeDatos.tablaEntrenador!!
                .crearEntrenador(
                    nombre.text.toString(),
                    descripcion.text.toString()
                )
            if(respuesta) mostrarSnackbar("Entrenador creado!")
        }

        // Actualizar entrenador
        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EbaseDeDatos.tablaEntrenador!!
                .actualizarEntrenadorFormulario(
                    nombre.text.toString(),
                    descripcion.text.toString(),
                    id.text.toString().toInt()
                )
            if(respuesta) mostrarSnackbar("Entrenador actualizado!")
        }

        // Eliminar entrenador
        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = EbaseDeDatos.tablaEntrenador!!
                .eliminarEntrenadorFormulario(
                    id.text.toString().toInt()
                )
            if(respuesta) mostrarSnackbar("Entrenador eliminado")
        }
    }
    fun mostrarSnackbar(Texto:String){
        val snack = Snackbar.make(findViewById(R.id.cl_sqlite), Texto, Snackbar.LENGTH_INDEFINITE)
        snack.show()
    }

}