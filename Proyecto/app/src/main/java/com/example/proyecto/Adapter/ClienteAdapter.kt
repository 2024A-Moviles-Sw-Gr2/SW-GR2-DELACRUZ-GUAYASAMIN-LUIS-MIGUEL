package com.example.proyecto.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R

// Modelo Cliente
data class Cliente(
    val nombre: String,
    val ci: String,
    val telefono: String
)

// Adaptador para RecyclerView
class ClienteAdapter(private val clientes: List<Cliente>, private val onEditClick: (Cliente) -> Unit, private val onDeleteClick: (Cliente) -> Unit) :
    RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

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
            ci.text = "CI: ${cliente.ci}"
            telefono.text = "Tlf: ${cliente.telefono}"

            // Manejar clics en los botones Editar y Eliminar
            btnEdit.setOnClickListener {
                onEditClick(cliente)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(cliente)
            }
        }
    }
}
