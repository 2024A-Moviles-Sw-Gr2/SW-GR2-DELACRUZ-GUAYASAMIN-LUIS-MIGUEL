package com.example.proyecto

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapter.CostoAdapter
import com.example.proyecto.modelo.Costo
import java.util.*

class CostosActiviy : AppCompatActivity() {
    private lateinit var costoAdapter: CostoAdapter
    private var costoParaEditar: Costo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_costos)

        val rvCostos = findViewById<RecyclerView>(R.id.recyclerViewCostos)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etMonto = findViewById<EditText>(R.id.etMonto)
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val etCategoria = findViewById<EditText>(R.id.etCategoria)
        val botonAddCosto = findViewById<Button>(R.id.btnAddCosto)
        val btnSelectDate = findViewById<ImageButton>(R.id.btnSelectDate)
        val btnLogout = findViewById<ImageView>(R.id.ivLogout)

        rvCostos.layoutManager = LinearLayoutManager(this)
        costoAdapter = CostoAdapter(
            costos = Costo.costos,
            onDeleteClick = { costo ->
                Costo.borrarCosto(costo)
                costoAdapter.notifyDataSetChanged()
            },
            onEditClick = { costo ->
                etDescripcion.setText(costo.descripcion)
                etMonto.setText(costo.monto.toString())
                etFecha.setText(costo.fecha)
                etCategoria.setText(costo.categoria)
                costoParaEditar = costo
            }
        )
        rvCostos.adapter = costoAdapter

        btnSelectDate.setOnClickListener {
            mostrarSelectorFecha(etFecha)
        }

        botonAddCosto.setOnClickListener {
            val descripcion = etDescripcion.text.toString()
            val monto = etMonto.text.toString().toDoubleOrNull()
            val fecha = etFecha.text.toString()
            val categoria = etCategoria.text.toString()

            if (descripcion.isNotEmpty() && monto != null && fecha.isNotEmpty() && categoria.isNotEmpty()) {
                if (costoParaEditar == null) {
                    // Agregar nuevo costo
                    val nuevoCosto = Costo(descripcion, monto, fecha, categoria)
                    Costo.agregarCosto(nuevoCosto)
                } else {
                    // Editar costo existente
                    val nuevoCosto = Costo(descripcion, monto, fecha, categoria)
                    Costo.editarCosto(costoParaEditar!!, nuevoCosto)
                    costoParaEditar = null // Reiniciar la variable de edición
                }

                costoAdapter.notifyDataSetChanged()

                etDescripcion.text.clear()
                etMonto.text.clear()
                etFecha.text.clear()
                etCategoria.text.clear()

                Toast.makeText(this, "Costo guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
    }

    private fun mostrarSelectorFecha(etFecha: EditText) {
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                etFecha.setText(fechaSeleccionada)
            },
            anio, mes, dia
        )
        datePicker.show()
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
