package com.example.proyecto.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.Modelo.Costo
import com.example.proyecto.R

class CostoAdapter(private val costos: List<Costo>, private val onEditClick: (Costo) -> Unit, private val onDeleteClick: (Costo) -> Unit) :
    RecyclerView.Adapter<CostoAdapter.CostoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_costo, parent, false)
        return CostoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CostoViewHolder, position: Int) {
        val costo = costos[position]
        holder.bind(costo)
    }

    override fun getItemCount(): Int = costos.size

    inner class CostoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        private val categoria: TextView = itemView.findViewById(R.id.tvCategoria)
        private val fecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val monto: TextView = itemView.findViewById(R.id.tvMonto)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(costo: Costo) {
            descripcion.text = costo.descripcion
            categoria.text = costo.categoria
            fecha.text = costo.fecha
            monto.text = "$${costo.monto}"

            // Manejar clics en los botones Editar y Eliminar
            btnEdit.setOnClickListener {
                onEditClick(costo)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(costo)
            }
        }
    }
}
