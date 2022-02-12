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


var recyclerWelcome : RecyclerView? = null
var recyclerJumpBack: RecyclerView? = null
var recyclerRecentlyPlayed : RecyclerView? = null
var recyclerMadeForU : RecyclerView? = null
var recyclerTopMixes : RecyclerView? = null
var recyclerUniquely : RecyclerView? = null
var recyclerMoreULike : RecyclerView? = null
/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentHome = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerWelcome = fragmentHome.findViewById(R.id.welcome_recycler)
        recyclerJumpBack = fragmentHome.findViewById(R.id.jump_back_recycler)
        recyclerRecentlyPlayed = fragmentHome.findViewById(R.id.top_genres_recycler)
        recyclerMadeForU = fragmentHome.findViewById(R.id.made_for_u_recycler)
        recyclerTopMixes = fragmentHome.findViewById(R.id.top_mixes_recycler)
        recyclerUniquely = fragmentHome.findViewById(R.id.uniquely_recycler)
        recyclerMoreULike = fragmentHome.findViewById(R.id.more_u_like_recycler)
        // Inflate the layout for this fragment
        return fragmentHome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listaItemsWelcome = arrayListOf<Cancion>()
        listaItemsWelcome.add(Cancion("hola","IMAGEN"))
        listaItemsWelcome.add(Cancion("hola","IMAGEN"))
        listaItemsWelcome.add(Cancion("hola","IMAGEN"))
        listaItemsWelcome.add(Cancion("hola","IMAGEN"))
        listaItemsWelcome.add(Cancion("hola","IMAGEN"))
        listaItemsWelcome.add(Cancion("hola","IMAGEN"))

        //DEBO CREAR UNA LISTA PARA CADA RECYCLER
        iniciarRecyclerViewWelcome(listaItemsWelcome,this, recyclerWelcome!!,AdaptadorWelcome(this,listaItemsWelcome, recyclerWelcome!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerJumpBack!!,AdaptadorNormalHome(this,listaItemsWelcome,recyclerJumpBack!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerRecentlyPlayed!!,AdaptadorRecentlyHome(this,listaItemsWelcome, recyclerRecentlyPlayed!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerMadeForU!!,AdaptadorNormalHome(this,listaItemsWelcome, recyclerMadeForU!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerTopMixes!!,AdaptadorNormalHome(this,listaItemsWelcome, recyclerTopMixes!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerUniquely!!,AdaptadorNormalHome(this,listaItemsWelcome, recyclerUniquely!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerMoreULike!!,AdaptadorNormalHome(this,listaItemsWelcome,recyclerMoreULike!!))
    }

    fun iniciarRecyclerViewWelcome(lista: List<*>, actividad: Home, recyclerView: RecyclerView, adaptador:RecyclerView.Adapter<*>){
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(context, 2)
       // recyclerView.layoutManager = LinearLayoutManager(actividad.context, LinearLayoutManager.HORIZONTAL, false)
        adaptador.notifyDataSetChanged()
    }

    fun iniciarRecyclerView( lista: List<*>,actividad: Home, recyclerView: RecyclerView, adaptador:RecyclerView.Adapter<*>){
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(actividad.context, LinearLayoutManager.HORIZONTAL, false)
        adaptador.notifyDataSetChanged()
    }
}