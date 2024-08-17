package com.example.proyecto.modelo

import java.io.Serializable

data class Costo(
    val descripcion: String,
    val monto: Double,
    val fecha: String,
    val categoria: String
) : Serializable {
    companion object {
        val costos = mutableListOf(
            Costo("Compra de repuestos", 300.0, "2024-08-01", "Repuestos"),
            Costo("Servicio de mantenimiento", 150.0, "2024-08-05", "Mantenimiento"),
            Costo("Pago de salarios", 1200.0, "2024-08-10", "Salarios"),
            Costo("Publicidad en redes sociales", 200.0, "2024-08-12", "Publicidad"),
            Costo("Alquiler de local", 800.0, "2024-08-15", "Alquiler")
        )

        fun agregarCosto(costo: Costo){
            costos.add(costo)
        }

        fun borrarCosto(costo: Costo){
            costos.remove(costo)
        }

        fun editarCosto(costoExistente: Costo, nuevoCosto: Costo){
            val index = costos.indexOf(costoExistente)
            if(index != -1){
                costos[index] = nuevoCosto
            }
        }
    }
}
