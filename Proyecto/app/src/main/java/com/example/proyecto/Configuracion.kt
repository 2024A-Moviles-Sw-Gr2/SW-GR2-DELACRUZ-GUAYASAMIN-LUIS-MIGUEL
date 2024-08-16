package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Configuracion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        val botonLogout = findViewById<Button>(R.id.btnCerrarSesion)

        ivLogout.setOnClickListener{
            irActividad(MainActivity::class.java)
        }

        botonLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}