package com.example.a2024aswgr2_luisdlc

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import kotlin.math.E

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.setOnClickListener {
            irActividaad(A_CicloVida::class.java)
        }
        val BotonList = findViewById<Button>(R.id.btn_list_view)
        BotonList.setOnClickListener{
            irActividaad(BListVew::class.java)
        }
        val botonIntImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntImplicito.setOnClickListener{
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackContenidoImplicito.launch(intentConRespuesta)
        }
        val botonIntExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntExplicito.setOnClickListener{
            val intentExplicito = Intent(this,CIntentExplicitoParametros::class.java)
            intentExplicito.putExtra("nombre", "Luis")
            intentExplicito.putExtra("apellido", "De La Cruz")
            intentExplicito.putExtra("edad", 22)
            intentExplicito.putExtra("entrenador", BEntrenador(10,"Stefano","Gomez"))
            callbackContenidoExplicito.launch(intentExplicito)

        }
        EbaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(
            this
        )

        val botonSQL = findViewById<Button>(R.id.btn_sqlite)
        botonSQL.setOnClickListener{
            irActividaad(ECrudEntrenador::class.java)
        }
        val botonRC = findViewById<Button>(R.id.rc_lista)
        botonRC.setOnClickListener{
            irActividaad(FRecyclerView::class.java)
        }
        val botonGMaps = findViewById<Button>(R.id.btn_google_maps)
        botonRC.setOnClickListener{
            irActividaad(GGoogleMapsActivity::class.java)
        }

    }
    fun irActividaad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
    fun mostrarSnackbar(Texto:String){
        val snack = Snackbar.make(findViewById(R.id.id_layout_main), Texto, Snackbar.LENGTH_INDEFINITE)
        snack.show()
    }
    val callbackContenidoExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                mostrarSnackbar("${data?.getStringExtra("nombreModificado")}")
            }
        }
    }
    val callbackContenidoImplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            //logica
            if(result.data != null){
                val uri: Uri = result.data!!.data!!
                val cursor = contentResolver.query(uri,null,null,null,null,null)
                cursor?.moveToFirst()
                val indiceTelefono = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val telefono = cursor?.getString(indiceTelefono!!)
                cursor?.close()
                mostrarSnackbar("telefono: $telefono")
            }

        }
    }
}