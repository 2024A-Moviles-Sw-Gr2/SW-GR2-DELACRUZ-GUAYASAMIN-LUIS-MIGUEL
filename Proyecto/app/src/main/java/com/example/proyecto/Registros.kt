package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Registros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        val botonRegistro = findViewById<Button>(R.id.btnAddRegistro)
        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        botonRegistro.setOnClickListener {
            irActividad(CRUD_registro::class.java)
        }

    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}