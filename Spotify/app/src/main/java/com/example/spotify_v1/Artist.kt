package com.example.spotify_v1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var recyclerPopular: RecyclerView? = null
var recyclerAlbums: RecyclerView? = null
private var btnBackArtist: androidx.appcompat.widget.Toolbar?= null
/**
 * A simple [Fragment] subclass.
 * Use the [Artist.newInstance] factory method to
 * create an instance of this fragment.
 */
class Artist : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentArtist = inflater.inflate(R.layout.fragment_artist, container, false)
        recyclerPopular = fragmentArtist.findViewById(R.id.recycler_popular)
        recyclerAlbums = fragmentArtist.findViewById(R.id.recycler_albums)
        btnBackArtist = fragmentArtist.findViewById(R.id.toolbar_Artist)
        // Inflate the layout for this fragment
        return fragmentArtist
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listaItemsLibrary = arrayListOf<Cancion>()
        val listaItemsAlbums = arrayListOf<Cancion>()
        for (i in 1..5) {
            listaItemsLibrary.add(Cancion("hola","IMAGEN"))
        }
        for (i in 1..10) {
            listaItemsAlbums.add(Cancion("hola","IMAGEN"))
        }
        //DEBO CREAR UNA LISTA PARA CADA RECYCLER
        iniciarRecyclerView(listaItemsLibrary,this, recyclerPopular!!,AdaptadorPopulares(this,listaItemsLibrary, recyclerPopular!!))
        iniciarRecyclerView(listaItemsAlbums,this, recyclerAlbums!!,AdaptadorAlbumes(this,listaItemsAlbums, recyclerAlbums!!))

        //BOTON BACK
        btnBackArtist?.setNavigationOnClickListener {
            val cambioFragment = Library()
            this.activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container,cambioFragment).commit()
            }
        }
    }

    fun iniciarRecyclerView(lista: List<*>, actividad: Artist, recyclerView: RecyclerView, adaptador:RecyclerView.Adapter<*>){
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(actividad.context, LinearLayoutManager.VERTICAL, false)
        adaptador.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Artist.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Artist().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}