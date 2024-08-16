package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Clientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        val botonAddClient = findViewById<Button>(R.id.btnAddClient)
        ivLogout.setOnClickListener{
            irActividad(MainActivity::class.java)
        }

        botonAddClient.setOnClickListener {
            irActividad(CRUD_Cliente::class.java)
        }

    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}