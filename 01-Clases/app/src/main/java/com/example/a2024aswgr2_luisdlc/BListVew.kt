package com.example.a2024aswgr2_luisdlc

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class BListVew : AppCompatActivity() {
    val arreglo =  BBaseDatosMemoria.arregloBentrenador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_vew)
        val listView = findViewById<ListView>(R.id.lv_listView)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, arreglo)
        listView.adapter =  adaptador
        adaptador.notifyDataSetChanged()
        val botonAnadirList = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAnadirList.setOnClickListener{anadirEntrenador(adaptador)}
        registerForContextMenu(listView)
    }
    fun anadirEntrenador(adaptador: ArrayAdapter<BEntrenador>){
        arreglo.add(BEntrenador(4,"Wendy","d@q.com"))
    }
    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                return true
            }

            R.id.mi_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                abriDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }
    fun abriDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar")
        builder.setPositiveButton("Aceptar",
            DialogInterface.OnClickListener{
                dialog, which -> mostrarSnackbar("Acepto $which")
            }

        )
        builder.setNegativeButton("Cancelar", null)
        val opciones = resources.getStringArray(R.array.string_array_opciones)
        val seleccionPrevia = booleanArrayOf(true,false,false)
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            {dialog, which, isChecked -> mostrarSnackbar("Item: $which")}
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun mostrarSnackbar(Texto:String){
        val snack = Snackbar.make(findViewById(R.id.cl_blist_view), Texto, Snackbar.LENGTH_INDEFINITE)
        snack.show()
    }
}