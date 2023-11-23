package com.sabanci.ovatify.fragment


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.adapter.HomeAdapter
import com.sabanci.ovatify.data.IhomeclickListener

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
        adapter= HomeAdapter(datalist,object : IhomeclickListener {
            override fun onItemClick(position: Int) {
                // Handle item click here
                val clickedItem = datalist[position]
                val intent = Intent(requireContext(), ShowMusicActivity::class.java)
                //    intent.putExtra("key", "value") will implement
                startActivity(intent)
                Toast.makeText(requireContext(), "Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.adapter=adapter
    }
    private fun datainitialize(){
        //no data is initialized just some space
        for(i in 1..20){
            datalist.add("")
        }
    }
}