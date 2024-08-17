package com.example.proyecto

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.proyecto.modelo.Cliente

class CRUD_ClienteActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_cliente)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        val botonAgregar = findViewById<Button>(R.id.btnAction)
        val nombre = findViewById<EditText>(R.id.etNombre)
        val ruc = findViewById<EditText>(R.id.etRUC)
        val telefono = findViewById<EditText>(R.id.etTelefono)
        val direccion = findViewById<EditText>(R.id.etDireccion)
        val correo = findViewById<EditText>(R.id.etCorreo)

        val clienteEditado = intent.getSerializableExtra("cliente", Cliente::class.java)

        clienteEditado?.let {
            nombre.setText(it.nombre)
            ruc.setText(it.ruc)
            telefono.setText(it.telefono)
            direccion.setText(it.direccion)
            correo.setText(it.correo)
        }

        botonAgregar.setOnClickListener {
            val nombreCliente = nombre.text.toString()
            val rucCliente = ruc.text.toString()
            val telefonoCliente = telefono.text.toString()
            val direccionCliente = direccion.text.toString()
            val correoCliente = correo.text.toString()

            val nuevoCliente = Cliente(nombreCliente, rucCliente, telefonoCliente, direccionCliente, correoCliente)

            if (clienteEditado != null) {
                Cliente.editarCliente(clienteEditado, nuevoCliente)
            } else {
                Cliente.agregarCliente(nuevoCliente)
            }

            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)

            finish()
        }
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
