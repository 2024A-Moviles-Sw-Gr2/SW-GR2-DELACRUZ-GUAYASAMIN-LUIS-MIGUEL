package com.example.deber02_sqlite

import android.os.Parcel
import android.os.Parcelable

class Curso(
    var id: Int,
    var materia: String,
    var numeroEstudiantes: Int,
    var codigo: String,
    var activo: Boolean,
    var duracionHoras: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(materia)
        parcel.writeInt(numeroEstudiantes)
        parcel.writeString(codigo)
        parcel.writeByte(if (activo) 1 else 0)
        parcel.writeDouble(duracionHoras)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Curso> {
        override fun createFromParcel(parcel: Parcel): Curso {
            return Curso(parcel)
        }

        override fun newArray(size: Int): Array<Curso?> {
            return arrayOfNulls(size)
        }
    }
}

