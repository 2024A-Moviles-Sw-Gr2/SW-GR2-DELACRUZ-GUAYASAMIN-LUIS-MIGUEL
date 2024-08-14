package com.example.deber02_sqlite

import android.os.Parcel
import android.os.Parcelable

data class Estudiante(
    var id: Int,
    var nombre: String,
    var edad: Int,
    var matricula: String,
    var fechaIngreso: String,
    var promedio: Double,
    var cursoId: Int? = null // Nueva propiedad para almacenar el ID del curso asociado
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readValue(Int::class.java.classLoader) as? Int // Leer el cursoId
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeInt(edad)
        parcel.writeString(matricula)
        parcel.writeString(fechaIngreso)
        parcel.writeDouble(promedio)
        parcel.writeValue(cursoId) // Escribir el cursoId
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }
}



