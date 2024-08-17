package com.example.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapter.ClienteAdapter
import com.example.proyecto.modelo.Cliente

class ClientesActivity : AppCompatActivity() {
    lateinit var verClientesLauncher: ActivityResultLauncher<Intent>
    private lateinit var clienteAdapter: ClienteAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        val botonAddClient = findViewById<Button>(R.id.btnAddClient)
        val rvClientes = findViewById<RecyclerView>(R.id.recyclerViewClientes)

        clienteAdapter = ClienteAdapter(
            context = this,
            clientes = Cliente.clientes,
            onEditClick = { cliente ->
                val intent = Intent(this, CRUD_ClienteActivity::class.java).apply {
                    putExtra("cliente", cliente)
                }
                verClientesLauncher.launch(intent)
            },
            onDeleteClick = { cliente ->
                Cliente.borrarCliente(cliente)
                clienteAdapter.notifyDataSetChanged()
            }
        )

        rvClientes.layoutManager = LinearLayoutManager(this)
        rvClientes.adapter = clienteAdapter

        ivLogout.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        botonAddClient.setOnClickListener {
            val intent = Intent(this, CRUD_ClienteActivity::class.java)
            verClientesLauncher.launch(intent)
        }

        verClientesLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                clienteAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
