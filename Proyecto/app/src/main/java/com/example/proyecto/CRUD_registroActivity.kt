package com.example.proyecto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.modelo.Cliente
import com.example.proyecto.modelo.Registro

class CRUD_registroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_registro)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        val botonGuardar = findViewById<Button>(R.id.btnGuardar)
        val spinnerCliente = findViewById<Spinner>(R.id.spinnerCliente)
        val placa = findViewById<EditText>(R.id.etPlaca)
        val modelo = findViewById<EditText>(R.id.etModelo)
        val spinnerServicio = findViewById<Spinner>(R.id.spinnerServicio)
        val descripcionActual = findViewById<EditText>(R.id.etDescripcionActual)
        val observaciones = findViewById<EditText>(R.id.etObservaciones)
        val precioServicio = findViewById<EditText>(R.id.etPrecioServicio)

        // Set up Spinner for Clients
        val clientes = Cliente.clientes
        val clienteAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, clientes.map { it.nombre })
        clienteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCliente.adapter = clienteAdapter

        // Set up Spinner for Services
        val servicios = listOf("Mantenimiento", "Reparación", "Inspección", "Servicio")
        val servicioAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, servicios)
        servicioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerServicio.adapter = servicioAdapter

        val registroEditado = intent.getSerializableExtra("registro") as? Registro

        registroEditado?.let {
            // Set default values based on the registro being edited
            val clienteIndex = clientes.indexOfFirst { it.nombre == registroEditado.cliente }
            if (clienteIndex != -1) {
                spinnerCliente.setSelection(clienteIndex)
            }
            placa.setText(it.placa)
            modelo.setText(it.modelo)
            spinnerServicio.setSelection(servicios.indexOf(it.tipoServicio))
            descripcionActual.setText(it.descripcion)
            observaciones.setText(it.observaciones)
            precioServicio.setText(it.monto.toString())
        }

        botonGuardar.setOnClickListener {
            val clienteSeleccionado = spinnerCliente.selectedItem.toString()
            val placaRegistro = placa.text.toString()
            val modeloRegistro = modelo.text.toString()
            val tipoServicioRegistro = spinnerServicio.selectedItem.toString()
            val descripcionRegistro = descripcionActual.text.toString()
            val observacionesRegistro = observaciones.text.toString()
            val montoRegistro = precioServicio.text.toString().toDoubleOrNull() ?: 0.0

            val nuevoRegistro = Registro(
                fecha = "2024-08-16",
                cliente = clienteSeleccionado,
                placa = placaRegistro,
                monto = montoRegistro,
                modelo = modeloRegistro,
                tipoServicio = tipoServicioRegistro,
                descripcion = descripcionRegistro,
                observaciones = observacionesRegistro
            )

            if (registroEditado != null) {
                Registro.editarRegistro(registroEditado, nuevoRegistro)
            } else {
                Registro.agregarRegistro(nuevoRegistro)
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
