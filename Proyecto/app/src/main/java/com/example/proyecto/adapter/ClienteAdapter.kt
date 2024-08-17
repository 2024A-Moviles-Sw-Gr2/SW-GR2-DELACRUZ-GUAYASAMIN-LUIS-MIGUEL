package com.example.proyecto.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.CRUD_ClienteActivity
import com.example.proyecto.ClientesActivity
import com.example.proyecto.R
import com.example.proyecto.modelo.Cliente

class ClienteAdapter(
    private val context: Context,
    private val clientes: MutableList<Cliente>,
    private val onEditClick: (Cliente) -> Unit,
    private val onDeleteClick: (Cliente) -> Unit
) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.bind(cliente)
    }

    override fun getItemCount(): Int = clientes.size

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val ci: TextView = itemView.findViewById(R.id.tvCI)
        private val telefono: TextView = itemView.findViewById(R.id.tvTelefono)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(cliente: Cliente) {
            nombre.text = cliente.nombre
            ci.text = "CI: ${cliente.ruc}"
            telefono.text = "Tlf: ${cliente.telefono}"

            btnEdit.setOnClickListener {
                val resultIntent = Intent(context, CRUD_ClienteActivity::class.java)
                resultIntent.putExtra("cliente", cliente)
                (context as ClientesActivity).verClientesLauncher.launch(resultIntent)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(cliente)
            }
        }
    }

    fun updateCliente(cliente: Cliente) {
        val position = clientes.indexOfFirst { it.ruc == cliente.ruc } // Use a unique identifier
        if (position != -1) {
            clientes[position] = cliente
            notifyItemChanged(position)
        }
    }
}
