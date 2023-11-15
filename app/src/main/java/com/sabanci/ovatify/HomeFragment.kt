package com.sabanci.ovatify

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment: Fragment(R.layout.home_fragment) {
    private lateinit var adapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist=ArrayList()
        datainitialize()
        val layoutManager=LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.recview)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter=HomeAdapter(datalist)
        recyclerView.adapter=adapter
    }
    private fun datainitialize(){
        //no data is initialized just some space
        for(i in 1..20){
            datalist.add("")
        }
    }
}