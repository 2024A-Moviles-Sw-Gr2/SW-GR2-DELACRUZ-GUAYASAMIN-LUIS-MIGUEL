package com.example.proyecto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.modelo.Repuesto
import com.example.proyecto.R

class RepuestoAdapter(private val repuestos: List<Repuesto>) :
    RecyclerView.Adapter<RepuestoAdapter.RepuestoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepuestoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repuesto, parent, false)
        return RepuestoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepuestoViewHolder, position: Int) {
        val repuesto = repuestos[position]
        holder.bind(repuesto)
    }

    override fun getItemCount(): Int = repuestos.size

    inner class RepuestoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        private val proveedor: TextView = itemView.findViewById(R.id.tvProveedor)
        private val porcentaje: TextView = itemView.findViewById(R.id.tvPorcentaje)
        private val precio: TextView = itemView.findViewById(R.id.tvPrecio)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(repuesto: Repuesto) {
            descripcion.text = repuesto.descripcion
            proveedor.text = repuesto.proveedor
            porcentaje.text = "${repuesto.porcentaje}%"
            precio.text = "$${repuesto.precio}"

            // Aquí puedes manejar los clics en los botones Editar y Eliminar
            btnEdit.setOnClickListener {
                // Código para editar repuesto
            }

            btnDelete.setOnClickListener {
                // Código para eliminar repuesto
            }
        }
    }
}
