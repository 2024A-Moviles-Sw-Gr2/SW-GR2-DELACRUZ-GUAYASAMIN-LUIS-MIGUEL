package com.example.examen

import android.os.Parcel
import android.os.Parcelable

data class Estudiante(
    var id: Int,
    var nombre: String,
    var edad: Int,
    var matricula: String,
    var fechaIngreso: String,
    var promedio: Double,
    var cursoId: Int? = null,
    var latitud: Double,
    var longitud: Double,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeInt(edad)
        parcel.writeString(matricula)
        parcel.writeString(fechaIngreso)
        parcel.writeDouble(promedio)
        parcel.writeValue(cursoId)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
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



