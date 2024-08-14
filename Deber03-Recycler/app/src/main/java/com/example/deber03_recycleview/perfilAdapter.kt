package com.example.deber03_recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class PerfilInfo(
    val name: String,
    val imageUrl: String,
    val isOnline: Boolean
)

class PerfilAdapter(private val profiles: List<PerfilInfo>) : RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder>() {

    class PerfilViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.profile_image)
        val username: TextView = view.findViewById(R.id.username)
        val statusIndicator: View = view.findViewById(R.id.status_indicator)

        fun bind(perfil: PerfilInfo) {
            Glide.with(profileImage.context)
                .load(perfil.imageUrl)
                .circleCrop()
                .into(profileImage)

            username.text = perfil.name
            statusIndicator.visibility = if (perfil.isOnline) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perfil, parent, false)
        return PerfilViewHolder(view)
    }

    override fun onBindViewHolder(holder: PerfilViewHolder, position: Int) {
        val perfil = profiles[position]
        holder.bind(perfil)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }
}