package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class CalendarioActiviy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        val botonCita = findViewById<Button>(R.id.btnAddCita)
        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        botonCita.setOnClickListener {
            irActividad(CRUD_citaActivity::class.java)
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}