package com.example.spotify_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorWelcome(private val actividad: Home, private val listaItemsWelcome: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorWelcome.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreItem: TextView
        val imagenItem: ImageView
        val btnItem: CardView

        init {
            nombreItem = view.findViewById(R.id.txt_tituloCancionPlaylist)
            imagenItem = view.findViewById(R.id.img_cancionPlaylist)
            btnItem = view.findViewById(R.id.cardview_item_Welcomehome)
        }
    }

    //Setear layout a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorWelcome.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_welcome, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    //SETEAMOS CADA ITEM
    override fun onBindViewHolder(holder: AdaptadorWelcome.MyViewHolder, position: Int) {
        val entrenador = listaItemsWelcome[position]
        holder.nombreItem.text = "Item $position"
        //SETEAR IMAGEN DE CADA WEA
       // holder.imagenItem
    }

    override fun getItemCount(): Int {
    return listaItemsWelcome.size
    }
}