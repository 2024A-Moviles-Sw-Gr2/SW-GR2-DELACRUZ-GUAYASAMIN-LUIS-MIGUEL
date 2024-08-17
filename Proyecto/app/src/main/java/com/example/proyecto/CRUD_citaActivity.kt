package com.example.proyecto

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.proyecto.modelo.Cita
import java.util.*

class CRUD_citaActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_cita)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val nombreCliente = findViewById<EditText>(R.id.etNombreCliente)
        val nombreCita = findViewById<EditText>(R.id.etNombreCita)
        val botonCrearCita = findViewById<Button>(R.id.btnCrearCita)

        val citaEditada = intent.getSerializableExtra("cita", Cita::class.java)

        // Pre-fill the fields if we are editing an existing cita
        citaEditada?.let {
            nombreCliente.setText(it.cliente)
            nombreCita.setText(it.servicio)
            val fecha = it.fecha.split("-")
            if (fecha.size == 3) {
                val year = fecha[0].toInt()
                val month = fecha[1].toInt() - 1 // Calendar months are 0-based
                val day = fecha[2].toInt()

                // Set the date on the CalendarView
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                calendarView.date = calendar.timeInMillis
            }
        }

        // Handle date selection
        var fechaSeleccionada: String = ""
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            fechaSeleccionada = "$year-${month + 1}-$dayOfMonth"
        }

        // Handle the "Create/Update Cita" button click
        botonCrearCita.setOnClickListener {
            val cliente = nombreCliente.text.toString()
            val servicio = nombreCita.text.toString()

            if (cliente.isNotEmpty() && servicio.isNotEmpty() && fechaSeleccionada.isNotEmpty()) {
                val nuevaCita = Cita(servicio, cliente, fechaSeleccionada)

                if (citaEditada != null) {
                    Cita.editarCita(citaEditada, nuevaCita)
                } else {
                    Cita.agregarCita(nuevaCita)
                }

                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                // Handle the case where fields are empty
            }
        }
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
