package com.example.spotify_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorPlaylist (private val actividad: DetallesHome, private val listaItemsPlaylist: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorPlaylist.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreCancionItem: TextView
        val artistaCancionItem:TextView
        val imagenCancionItem: ImageView
        val btnItem: LinearLayout

        init {
            nombreCancionItem = view.findViewById(R.id.txt_tituloCancionPlaylist)
            artistaCancionItem = view.findViewById(R.id.txt_artistaCancionPlaylist)
            imagenCancionItem = view.findViewById(R.id.img_cancionPlaylist)
            btnItem = view.findViewById(R.id.layout_cancionPlaylist)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorPlaylist.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_playlist, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorPlaylist.MyViewHolder, position: Int) {
        val cancionPlaylist = listaItemsPlaylist[position]
        //holder.nombreItem.text = "Item $position"
        //SETEAR IMAGEN DE CADA WEA
        // holder.imagenItem
    }

    override fun getItemCount(): Int {
        return listaItemsPlaylist.size
    }

}