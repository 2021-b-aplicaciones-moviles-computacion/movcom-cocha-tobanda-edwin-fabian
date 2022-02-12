package com.example.spotify_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorAlbumes  (private val actividad: Artist, private val listaItemsAlbumes: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorAlbumes.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagenItem: ImageView
        val nombreItem: TextView
        val anioRelease: TextView
        val btnItem: View

        init {

            nombreItem = view.findViewById(R.id.txt_tituloAlbumArtist)
            anioRelease = view.findViewById(R.id.txt_anioRelease)
            imagenItem = view.findViewById(R.id.img_AlbumArtist)
            btnItem = view.findViewById(R.id.item_albumArtist_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorAlbumes.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_album, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorAlbumes.MyViewHolder, position: Int) {
        val popular = listaItemsAlbumes[position]
        //holder.numberItem.text = " ${position+1}"
        //SETEAR IMAGEN DE CADA WEA
        // holder.imagenItem
    }

    override fun getItemCount(): Int {
        return listaItemsAlbumes.size
    }

}