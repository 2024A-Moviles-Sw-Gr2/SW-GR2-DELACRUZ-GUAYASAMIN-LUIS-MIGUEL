package com.example.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapter.ClienteAdapter
import com.example.proyecto.adapter.RegistroAdapter
import com.example.proyecto.modelo.Registro

class RegistrosActivity : AppCompatActivity() {
    private lateinit var verRegistrosLauncher: ActivityResultLauncher<Intent>
    private lateinit var registroAdapter: RegistroAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        val botonRegistro = findViewById<Button>(R.id.btnAddRegistro)
        val rvRegistros = findViewById<RecyclerView>(R.id.recyclerViewRegistros)

        // Set up RecyclerView with the adapter
        rvRegistros.layoutManager = LinearLayoutManager(this)
        registroAdapter = RegistroAdapter(
            registros = Registro.registros,
            onPrintClick = { registro ->
                // Handle print action
            },
            onEditClick = { registro ->
                val intent = Intent(this, CRUD_registroActivity::class.java)
                intent.putExtra("registro", registro)
                verRegistrosLauncher.launch(intent)
            },
            onDeleteClick = { registro ->
                Registro.borrarRegistro(registro)
                registroAdapter.notifyDataSetChanged()
            }
        )
        rvRegistros.adapter = registroAdapter

        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        botonRegistro.setOnClickListener {
            val intent = Intent(this, CRUD_registroActivity::class.java)
            verRegistrosLauncher.launch(intent)
        }

        verRegistrosLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                rvRegistros.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
