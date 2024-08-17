package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapter.RepuestoAdapter
import com.example.proyecto.modelo.Repuesto

class RepuestosActivity : AppCompatActivity() {
    private lateinit var repuestoAdapter: RepuestoAdapter
    private var repuestoEditado: Repuesto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repuestos)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        val rvRepuestos = findViewById<RecyclerView>(R.id.recyclerViewRepuestos)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etPorcentaje = findViewById<EditText>(R.id.etPorcentaje)
        val etProveedor = findViewById<EditText>(R.id.etProveedor)
        val botonAddRepuesto = findViewById<Button>(R.id.btnAddRepuesto)

        // Inicializar el RecyclerView y Adapter
        rvRepuestos.layoutManager = LinearLayoutManager(this)
        repuestoAdapter = RepuestoAdapter(
            repuestos = Repuesto.repuestos,
            onDeleteClick = { repuesto ->
                Repuesto.borrarRepuesto(repuesto)
                repuestoAdapter.notifyDataSetChanged()
            },
            onEditClick = { repuesto ->
                // Establece los datos en los campos de edición
                etDescripcion.setText(repuesto.descripcion)
                etPrecio.setText(repuesto.precio.toString())
                etPorcentaje.setText(repuesto.porcentaje.toString())
                etProveedor.setText(repuesto.proveedor)
                repuestoEditado = repuesto
            }
        )
        rvRepuestos.adapter = repuestoAdapter

        // Botón para agregar o editar un repuesto
        botonAddRepuesto.setOnClickListener {
            val descripcion = etDescripcion.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull()
            val porcentaje = etPorcentaje.text.toString().toIntOrNull()
            val proveedor = etProveedor.text.toString()

            if (descripcion.isNotEmpty() && precio != null && porcentaje != null && proveedor.isNotEmpty()) {
                if (repuestoEditado == null) {
                    // Agregar nuevo repuesto
                    val nuevoRepuesto = Repuesto(descripcion, precio, porcentaje, proveedor)
                    Repuesto.agregarRepuesto(nuevoRepuesto)
                    Toast.makeText(this, "Repuesto agregado", Toast.LENGTH_SHORT).show()
                } else {
                    // Editar repuesto existente usando el método de la clase Repuesto
                    Repuesto.editarRepuesto(
                        repuestoOriginal = repuestoEditado!!,
                        nuevaDescripcion = descripcion,
                        nuevoPrecio = precio,
                        nuevoPorcentaje = porcentaje,
                        nuevoProveedor = proveedor
                    )
                    Toast.makeText(this, "Repuesto editado", Toast.LENGTH_SHORT).show()
                    repuestoEditado = null
                }

                // Actualizar la lista y limpiar los campos
                repuestoAdapter.notifyDataSetChanged()
                etDescripcion.text.clear()
                etPrecio.text.clear()
                etPorcentaje.text.clear()
                etProveedor.text.clear()
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
