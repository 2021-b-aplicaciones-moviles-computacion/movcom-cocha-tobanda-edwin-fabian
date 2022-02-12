package com.example.spotify_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class AdaptadorLibrary (private val actividad: Library, private val listaItemsLibrary: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorLibrary.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnItem: LinearLayout
        init {
            btnItem = view.findViewById(R.id.btn_artist_layout)

            btnItem.setOnClickListener {
                val cambioFragment = Artist()
                actividad.activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.frame_container,cambioFragment).commit()
                }
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorLibrary.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_artist, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorLibrary.MyViewHolder, position: Int) {
        val album = listaItemsLibrary[position]
        //SETEAR IMAGEN DE CADA WEA
        // holder.imagenItem
    }

    override fun getItemCount(): Int {
        return listaItemsLibrary.size
    }


}