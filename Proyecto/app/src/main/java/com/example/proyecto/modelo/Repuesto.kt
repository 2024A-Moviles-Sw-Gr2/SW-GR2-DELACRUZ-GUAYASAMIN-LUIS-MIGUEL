package com.example.proyecto.modelo

import java.io.Serializable

data class Repuesto(
    var descripcion: String,
    var precio: Double,
    var porcentaje: Int,
    var proveedor: String
) : Serializable {
    companion object {
        // Lista de repuestos en memoria
        val repuestos = mutableListOf<Repuesto>(
            Repuesto("Filtro de aire", 15.50, 10, "Proveedor A"),
            Repuesto("Aceite de motor", 25.00, 15, "Proveedor B"),
            Repuesto("Bujía", 5.00, 5, "Proveedor C"),
            Repuesto("Frenos", 40.00, 12, "Proveedor D")
        )
        fun agregarRepuesto(repuesto: Repuesto) {
            repuestos.add(repuesto)
        }

        fun borrarRepuesto(repuesto: Repuesto) {
            repuestos.remove(repuesto)
        }

        fun editarRepuesto(
            repuestoOriginal: Repuesto,
            nuevaDescripcion: String,
            nuevoPrecio: Double,
            nuevoPorcentaje: Int,
            nuevoProveedor: String
        ) {
            repuestoOriginal.descripcion = nuevaDescripcion
            repuestoOriginal.precio = nuevoPrecio
            repuestoOriginal.porcentaje = nuevoPorcentaje
            repuestoOriginal.proveedor = nuevoProveedor
        }
    }
}
