package com.example.proyecto.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.Modelo.Registro
import com.example.proyecto.R

class RegistroAdapter(private val registros: List<Registro>, private val onPrintClick: (Registro) -> Unit, private val onEditClick: (Registro) -> Unit, private val onDeleteClick: (Registro) -> Unit) :
    RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registro, parent, false)
        return RegistroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val registro = registros[position]
        holder.bind(registro)
    }

    override fun getItemCount(): Int = registros.size

    inner class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val cliente: TextView = itemView.findViewById(R.id.tvCliente)
        private val placa: TextView = itemView.findViewById(R.id.tvPlaca)
        private val monto: TextView = itemView.findViewById(R.id.tvMonto)
        private val btnPrint: ImageButton = itemView.findViewById(R.id.btnPrint)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(registro: Registro) {
            fecha.text = registro.fecha
            cliente.text = "Cliente: ${registro.cliente}"
            placa.text = "Placa: ${registro.placa}"
            monto.text = "$${registro.monto}"

            // Manejar clics en los botones Imprimir, Editar y Eliminar
            btnPrint.setOnClickListener {
                onPrintClick(registro)
            }

            btnEdit.setOnClickListener {
                onEditClick(registro)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(registro)
            }
        }
    }
}
