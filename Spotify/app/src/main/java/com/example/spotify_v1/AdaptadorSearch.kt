package com.example.spotify_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class AdaptadorSearch (private val actividad: Search, private val listaItemsSearch: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorSearch.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnItem: ImageButton

        init {
            btnItem = view.findViewById(R.id.btn_item_search)
            btnItem.setOnClickListener {
                val cambioFragment = LibreriaGenero()
                actividad.activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.frame_container,cambioFragment).commit()
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorSearch.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_search, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorSearch.MyViewHolder, position: Int) {
        val album = listaItemsSearch[position]
        //SETEAR IMAGEN DE CADA WEA
        // holder.imagenItem
    }

    override fun getItemCount(): Int {
        return listaItemsSearch.size
    }
}