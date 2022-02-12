package com.example.spotify_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorRecentlyHome (private val actividad: Home, private val listaItemsRecently: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorRecentlyHome.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreItem: TextView
        val imagenItem: ImageView
        val btnItem: CardView

        init {
            nombreItem = view.findViewById(R.id.txt_itemCard_recently)
            imagenItem = view.findViewById(R.id.img_item_recently)
            btnItem = view.findViewById(R.id.cardview_item_recently)
        }
    }

    //Setear layout a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorRecentlyHome.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recently_played, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    //SETEAMOS CADA ITEM
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenador = listaItemsRecently[position]
        //holder.nombreItem.text = "Item $position"
        //SETEAR IMAGEN DE CADA WEA
        // holder.imagenItem
    }

    override fun getItemCount(): Int {
        return listaItemsRecently.size
    }


}