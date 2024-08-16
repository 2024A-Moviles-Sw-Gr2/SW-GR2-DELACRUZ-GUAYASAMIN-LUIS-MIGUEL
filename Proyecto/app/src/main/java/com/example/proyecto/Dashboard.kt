package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val configuracion = findViewById<ImageView>(R.id.ivSettings)
        val botonCliente = findViewById<Button>(R.id.btnCliente)
        val botonCostos = findViewById<Button>(R.id.btnCostos)
        val botonRegistros = findViewById<Button>(R.id.btnRegistros)
        val botonRepuestos = findViewById<Button>(R.id.btnRepuestos)
        val botonCitas = findViewById<Button>(R.id.btnCitas)

        configuracion.setOnClickListener{
            irActividad(Configuracion::class.java)
        }

        botonCliente.setOnClickListener {
            irActividad(Clientes::class.java)
        }

        botonCostos.setOnClickListener {
            irActividad(Costos::class.java)
        }

        botonRegistros.setOnClickListener {
            irActividad(Registros::class.java)
        }

        botonRepuestos.setOnClickListener {
            irActividad(Repuestos::class.java)
        }

        botonCitas.setOnClickListener {
            irActividad(Calendario::class.java)
        }

    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}