package com.example.deber02_sqlite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter(context: Context, cursos: List<Curso>) : ArrayAdapter<Curso>(context, 0, cursos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val curso = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

        val textViewTitulo = view.findViewById<TextView>(R.id.textViewTitulo)
        textViewTitulo.text = curso?.materia

        return view
    }
}

