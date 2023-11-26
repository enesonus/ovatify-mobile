package com.sabanci.ovatify.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.adapter.HomeAdapter
import com.sabanci.ovatify.databinding.ActivityMainBinding
import com.sabanci.ovatify.databinding.HomeFragment2Binding

class HomeFragment2 : Fragment(R.layout.home_fragment2) {

    private lateinit var adapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
    private lateinit var binding: HomeFragment2Binding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //datalist=ArrayList()
        //datainitialize()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)

        //adapter= HomeAdapter(datalist)
        //recyclerView.adapter=adapter




    }

    private fun datainitialize(){
        //no data is initialized just some space
        for(i in 1..20){
            datalist.add("")
        }
    }

}