package com.example.spotify_v1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var recyclerTopGenres : RecyclerView? = null
var recyclerBrowseAll : RecyclerView? = null
/**
 * A simple [Fragment] subclass.
 * Use the [Search.newInstance] factory method to
 * create an instance of this fragment.
 */
class Search : Fragment() {
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
        val fragmentSearch = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerTopGenres = fragmentSearch.findViewById(R.id.top_genres_recycler)
        recyclerBrowseAll = fragmentSearch.findViewById(R.id.browse_all_recycler)
        // Inflate the layout for this fragment
        return fragmentSearch

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listaItemsTopGenres = arrayListOf<Cancion>()
        listaItemsTopGenres.add(Cancion("hola","IMAGEN"))
        listaItemsTopGenres.add(Cancion("hola","IMAGEN"))
        listaItemsTopGenres.add(Cancion("hola","IMAGEN"))
        listaItemsTopGenres.add(Cancion("hola","IMAGEN"))

        val listaItemsBrowseAll = arrayListOf<Cancion>()
        for (i in 1..60) {
            listaItemsBrowseAll.add(Cancion("hola","IMAGEN"))
        }

        //DEBO CREAR UNA LISTA PARA CADA RECYCLER
        iniciarRecyclerViewWelcome(listaItemsTopGenres,this, recyclerTopGenres!!,AdaptadorSearch(this,listaItemsTopGenres, recyclerTopGenres!!))
        iniciarRecyclerViewWelcome(listaItemsBrowseAll,this, recyclerBrowseAll!!,AdaptadorSearch(this,listaItemsBrowseAll, recyclerBrowseAll!!))
    }

    fun iniciarRecyclerViewWelcome(lista: List<*>, actividad: Search, recyclerView: RecyclerView, adaptador:RecyclerView.Adapter<*>){
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        // recyclerView.layoutManager = LinearLayoutManager(actividad.context, LinearLayoutManager.HORIZONTAL, false)
        adaptador.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Search.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Search().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}