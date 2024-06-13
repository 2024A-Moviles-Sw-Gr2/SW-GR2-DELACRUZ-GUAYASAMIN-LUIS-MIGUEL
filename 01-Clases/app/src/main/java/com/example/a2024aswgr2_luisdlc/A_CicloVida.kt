package com.example.a2024aswgr2_luisdlc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar

class A_CicloVida : AppCompatActivity() {
    var textoGlobal = ""
    fun mostrarSnackbar(Texto:String){
    textoGlobal+=Texto
        val snack = Snackbar.make(findViewById(R.id.cl_ciclo_vida), textoGlobal,Snackbar.LENGTH_INDEFINITE)
        snack.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("OnCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("OnStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("OnResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("OnRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("OnPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackbar("OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("OnDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("VariableTextoGuardado", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val textoRecuperado:String?= savedInstanceState.getString("variableTextoGuardado")
        if (textoRecuperado != null) {
            mostrarSnackbar(textoRecuperado)
            textoGlobal = textoRecuperado
        }

    }
}


