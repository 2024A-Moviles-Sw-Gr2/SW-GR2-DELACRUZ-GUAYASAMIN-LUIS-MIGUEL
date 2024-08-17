package com.example.proyecto.modelo

import java.io.Serializable

data class Registro(
    val fecha: String,
    val cliente: String,
    val placa: String,
    val monto: Double,
    val modelo: String,
    val tipoServicio: String,
    val descripcion: String,
    val observaciones: String
) : Serializable {
    companion object {
        val registros = mutableListOf(
            Registro("2024-08-16", "Juan Perez", "ABC123", 100.0, "Toyota Camry", "Mantenimiento", "Cambio de aceite", "Ninguna"),
            Registro("2024-08-17", "Maria Lopez", "XYZ789", 150.0, "Honda Civic", "Reparación", "Cambio de frenos", "Urgente"),
            Registro("2024-08-18", "Carlos Ruiz", "LMN456", 200.0, "Ford Focus", "Inspección", "Revisión general", "A revisar"),
            Registro("2024-08-19", "Ana Torres", "DEF321", 250.0, "Chevrolet Malibu", "Servicio", "Cambio de neumáticos", "Buen estado")
        )

        fun agregarRegistro(registro: Registro) {
            registros.add(registro)
        }

        fun borrarRegistro(registro: Registro) {
            registros.remove(registro)
        }

        fun editarRegistro(registroExistente: Registro, nuevoRegistro: Registro) {
            val index = registros.indexOf(registroExistente)
            if (index != -1) {
                registros[index] = nuevoRegistro
            }
        }
    }
}
