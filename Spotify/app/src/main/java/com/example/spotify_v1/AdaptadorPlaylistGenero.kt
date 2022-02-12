package com.example.spotify_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorPlaylistGenero  (private val actividad: LibreriaGenero, private val listaItemsGenero: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorPlaylistGenero.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombrePlaylistItem: TextView
        val numFollowersItem: TextView
        val imagenPlaylistItem: ImageView
        val btnItem: LinearLayout

        init {
            nombrePlaylistItem = view.findViewById(R.id.txt_nombre_playlist)
            numFollowersItem = view.findViewById(R.id.txt_followers_library)
            imagenPlaylistItem = view.findViewById(R.id.img_artist_item_library)
            btnItem = view.findViewById(R.id.btn_album_libreria)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorPlaylistGenero.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_libreria, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorPlaylistGenero.MyViewHolder, position: Int) {
        val cancionPlaylist = listaItemsGenero[position]
        //holder.nombreItem.text = "Item $position"
        //SETEAR IMAGEN DE CADA WEA
        // holder.imagenItem
    }

    override fun getItemCount(): Int {
        return listaItemsGenero.size
    }
}