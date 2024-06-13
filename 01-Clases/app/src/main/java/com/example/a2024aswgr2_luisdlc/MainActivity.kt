package com.example.a2024aswgr2_luisdlc

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.setOnClickListener {
            irActividaad(A_CicloVida::class.java)
        }
    }
    fun irActividaad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}