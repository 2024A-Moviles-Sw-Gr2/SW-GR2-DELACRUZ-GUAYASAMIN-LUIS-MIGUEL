package com.example.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapter.CitaAdapter
import com.example.proyecto.adapter.RegistroAdapter
import com.example.proyecto.modelo.Cita

class CalendarioActiviy : AppCompatActivity() {
    private lateinit var verCitasLauncher: ActivityResultLauncher<Intent>
    private lateinit var citaAdapter: CitaAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        val botonCita = findViewById<Button>(R.id.btnAddCita)
        val rvCitas = findViewById<RecyclerView>(R.id.recyclerViewCitas)

        rvCitas.layoutManager = LinearLayoutManager(this)
        citaAdapter = CitaAdapter(
            citas = Cita.citas,
            onEditClick = {cita ->
                val intent = Intent(this, CRUD_citaActivity::class.java)
                intent.putExtra("cita", cita)
                verCitasLauncher.launch(intent)
            },
            onDeleteClick = {cita ->
                Cita.borrarCita(cita)
                citaAdapter.notifyDataSetChanged()
            }
        )

        rvCitas.adapter = citaAdapter

        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        botonCita.setOnClickListener {
            val intent = Intent(this, CRUD_citaActivity::class.java)
            verCitasLauncher.launch(intent)
        }

        verCitasLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if (result.resultCode == RESULT_OK){
                rvCitas.adapter?.notifyDataSetChanged()
            }
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}