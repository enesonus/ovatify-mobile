package com.sabanci.ovatify.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.adapter.HomeAdapter
import com.sabanci.ovatify.adapter.LibraryAdapter2
import com.sabanci.ovatify.databinding.MusicListBinding
import com.sabanci.ovatify.databinding.MusicParentItemBinding
import com.sabanci.ovatify.utils.SampleData

class ExploreFragment:Fragment(R.layout.explore_fragment) {
    private lateinit var binding: MusicListBinding
    private lateinit var adapter: LibraryAdapter2
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        datalist=ArrayList()
        datainitialize()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.recview)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= HomeAdapter(datalist)
        recyclerView.adapter=adapter
         */

        //recyclerView = view.findViewById(R.id.library_recycler_view)
        //recyclerView.adapter=LibraryAdapter2(SampleData.collections)



    }
    private fun datainitialize(){
        //no data is initialized just some space
        for(i in 1..20){
            datalist.add("")
        }
    }
}