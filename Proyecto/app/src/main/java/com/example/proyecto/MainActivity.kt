package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonLogin = findViewById<Button>(R.id.btnLogin)
        val usuario = findViewById<EditText>(R.id.etUsername)
        val contrasena = findViewById<EditText>(R.id.etPassword)
        botonLogin.setOnClickListener {
            if((usuario.toString() == "sebastian") && (contrasena.toString() == "admin")){
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
            }
        }
    }
}