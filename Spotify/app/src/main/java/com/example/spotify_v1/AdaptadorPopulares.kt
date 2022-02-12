package com.example.spotify_v1

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorPopulares (private val actividad: Artist, private val listaPopulares: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorPopulares.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberItem: TextView
        val imagenItem: ImageView
        val nombreItem: TextView
        val numReproducciones: TextView
        val btnItem: View

        init {
            numberItem = view.findViewById(R.id.txt_numberPopular)
            nombreItem = view.findViewById(R.id.txt_nombrePopular)
            numReproducciones = view.findViewById(R.id.txt_numReproducciones)
            imagenItem = view.findViewById(R.id.img_popular)
            btnItem = view.findViewById(R.id.item_popular_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorPopulares.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_popular, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorPopulares.MyViewHolder, position: Int) {
        val popular = listaPopulares[position]
        holder.numberItem.text = " ${position+1}"
        //SETEAR IMAGEN DE CADA WEA
        // holder.imagenItem
    }

    override fun getItemCount(): Int {
        return listaPopulares.size
    }
}