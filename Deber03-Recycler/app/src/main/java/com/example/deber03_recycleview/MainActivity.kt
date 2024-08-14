package com.example.deber03_recycleview

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button_home)
        // Inicializar RecyclerView para Perfiles
        val recyclerViewPerfiles = findViewById<RecyclerView>(R.id.recyclerViewPerfiles)
        inicializarRecyclerViewPerfiles(recyclerViewPerfiles)

        // Inicializar RecyclerView para Chats
        val recyclerViewChats = findViewById<RecyclerView>(R.id.recyclerViewChats)
        inicializarRecyclerViewChats(recyclerViewChats)
    }

    private fun inicializarRecyclerViewPerfiles(recyclerView: RecyclerView) {
        val profiles = (1..10).map {
            PerfilInfo(
                name = "Usuario $it",
                imageUrl = "https://picsum.photos/50?random=$it",
                isOnline = it % 2 == 0
            )
        }
        val adaptador = PerfilAdapter(profiles)
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adaptador.notifyDataSetChanged()
    }

    private fun inicializarRecyclerViewChats(recyclerView: RecyclerView) {
        val chats = (1..10).map {
            ChatItem(
                name = "Contacto $it",
                lastMessage = "Mensaje reciente $it",
                date = "Fecha $it",
                profileImageUrl = "https://picsum.photos/50?random=${it + 10}",
                statusImageUrl = "https://picsum.photos/20?random=${it + 20}"
            )
        }
        val adaptador = ChatAdapter(chats)
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
}

