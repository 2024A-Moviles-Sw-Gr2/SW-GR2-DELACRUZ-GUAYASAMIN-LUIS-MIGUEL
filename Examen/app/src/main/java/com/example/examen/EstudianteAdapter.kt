package com.example.examen
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class EstudianteAdapter(context: Context, estudiantes: List<Estudiante>) : ArrayAdapter<Estudiante>(context, 0, estudiantes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val estudiante = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

        val textViewTitulo = view.findViewById<TextView>(R.id.textViewTitulo)
        textViewTitulo.text = estudiante?.nombre

        return view
    }
}