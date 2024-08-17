package com.example.proyecto.modelo

import java.io.Serializable

data class Cita(
    var servicio: String,
    var cliente: String,
    var fecha: String // Campo para la fecha de la cita
) : Serializable {
    companion object {
        val citas: MutableList<Cita> = mutableListOf(
            Cita("Mantenimiento", "Juan Pérez", "2024-08-20"),
            Cita("Cambio de aceite", "María García", "2024-08-21")
        )

        fun agregarCita(cita: Cita) {
            citas.add(cita)
        }

        fun borrarCita(cita: Cita) {
            citas.remove(cita)
        }

        fun editarCita(citaEditada: Cita, nuevoCita: Cita) {
            val index = citas.indexOf(citaEditada)
            if (index != -1) {
                citas[index] = nuevoCita
            }
        }
    }
}
