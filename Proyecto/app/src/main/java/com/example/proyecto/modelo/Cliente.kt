package com.example.proyecto.modelo

import java.io.Serializable

data class Cliente(
    var nombre: String,
    var ruc: String,
    var telefono: String,
    var direccion: String,
    var correo: String
) : Serializable {
    companion object {
        val clientes = mutableListOf(
            Cliente("Juan Perez", "1234567890", "0987654321", "Quito", "juan@example.com"),
            Cliente("Maria Lopez", "0987654321", "0998765432", "Guayaquil", "maria@example.com"),
            Cliente("Carlos Ruiz", "1122334455", "0991234567", "Cuenca", "carlos@example.com"),
            Cliente("Ana Torres", "2233445566", "0987654321", "Loja", "ana@example.com")
        )

        fun agregarCliente(cliente: Cliente) {
            clientes.add(cliente)
        }

        fun borrarCliente(cliente: Cliente) {
            clientes.remove(cliente)
        }

        fun editarCliente(clienteExistente: Cliente, nuevoCliente: Cliente) {
            val index = clientes.indexOf(clienteExistente)
            if (index != -1) {
                clientes[index] = nuevoCliente
            }
        }
    }
}
