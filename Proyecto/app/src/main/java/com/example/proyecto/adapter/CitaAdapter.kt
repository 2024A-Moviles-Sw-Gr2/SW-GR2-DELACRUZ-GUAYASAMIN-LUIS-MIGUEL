package com.example.proyecto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.modelo.Cita
import com.example.proyecto.R

class CitaAdapter(
    private val citas: List<Cita>,
    private val onEditClick: (Cita) -> Unit,
    private val onDeleteClick: (Cita) -> Unit) :
    RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.bind(cita)
    }

    override fun getItemCount(): Int = citas.size

    inner class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val servicio: TextView = itemView.findViewById(R.id.tvServicio)
        private val cliente: TextView = itemView.findViewById(R.id.tvCliente)
        private val fecha: TextView = itemView.findViewById(R.id.tvFecha_cita)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(cita: Cita) {
            servicio.text = cita.servicio
            cliente.text = cita.cliente
            fecha.text = cita.fecha  // Vincula la fecha al TextView

            // Manejar clics en los botones Editar y Eliminar
            btnEdit.setOnClickListener {
                onEditClick(cita)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(cita)
            }
        }
    }
}
