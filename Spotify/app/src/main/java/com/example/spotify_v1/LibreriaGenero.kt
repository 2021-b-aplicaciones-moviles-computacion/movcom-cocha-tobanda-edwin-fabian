package com.example.spotify_v1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var recyclerLibraryGenero: RecyclerView? = null
var btnBackSearch: androidx.appcompat.widget.Toolbar?= null
/**
 * A simple [Fragment] subclass.
 * Use the [LibreriaGenero.newInstance] factory method to
 * create an instance of this fragment.
 */
class LibreriaGenero : Fragment() {
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
        val fragmentLibraryGenero = inflater.inflate(R.layout.fragment_libreria_genero, container, false)
        recyclerLibraryGenero = fragmentLibraryGenero.findViewById(R.id.recycler_playlistGeneros)
        btnBackSearch= fragmentLibraryGenero.findViewById(R.id.toolbar_libreria_genero)
        // Inflate the layout for this fragment
        return fragmentLibraryGenero
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listaItemsLibrary = arrayListOf<Cancion>()
        for (i in 1..20) {
            listaItemsLibrary.add(Cancion("hola","IMAGEN"))
        }
        //DEBO CREAR UNA LISTA PARA CADA RECYCLER
        iniciarRecyclerView(listaItemsLibrary,this, recyclerLibraryGenero!!,AdaptadorPlaylistGenero(this,listaItemsLibrary, recyclerLibraryGenero!!))

            //BACK
        btnBackSearch?.setNavigationOnClickListener {
            val cambioFragment = Search()
            this.activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container,cambioFragment).commit()
            }
        }
    }

    fun iniciarRecyclerView(lista: List<*>, actividad: LibreriaGenero, recyclerView: RecyclerView, adaptador:RecyclerView.Adapter<*>){
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adaptador.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LibreriaGenero.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LibreriaGenero().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}