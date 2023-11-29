package com.sabanci.ovatify.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.adapter.DashboardAdapter
import com.sabanci.ovatify.adapter.HomeAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.BarChartData
import com.sabanci.ovatify.data.IhomeclickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment:Fragment(R.layout.dashboard_fragment) {
    private lateinit var adapter: DashboardAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<BarChartData> //data is not initialized yet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist=ArrayList()
        datainitialize()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.dashboard_recview)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= DashboardAdapter(datalist
        /*,object : IhomeclickListener {
            override fun onItemClick(position: Int) {
                // Handle item click here
                val clickedItem = datalist[position]
                val intent = Intent(requireContext(), ShowMusicActivity::class.java)
                //    intent.putExtra("key", "value") will implement
                startActivity(intent)
                Toast.makeText(requireContext(), "Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
            }
        }*/
        )
        recyclerView.adapter=adapter
    }
    private fun datainitialize(){
       /* val valuelist=ArrayList<Float>()
        valuelist.add(5F)
        valuelist.add(4F)
        valuelist.add(3F)
        val descrp="selam kanzi"
        val namelist=ArrayList<String>()
        namelist.add("bir")
        namelist.add("iki")
        namelist.add("üc")
        val barChartData=BarChartData(valuelist,descrp,namelist)
        datalist.add(barChartData)*/
        //data will be initialized here
        val callforgenre = RetrofitClient.apiService.getEntityCount(entity = "genres")
        callforgenre.enqueue(object : Callback<Map<String, Float>> {
            override fun onResponse(call: Call<Map<String, Float>>, response: Response<Map<String, Float>>) {
                if (response.isSuccessful) {
                    val genreCounts = response.body() ?: emptyMap()
                    // Handle successful response, e.g., update UI with genre counts
                    val genreNames = ArrayList<String>(genreCounts.keys)
                    val genreCountsList = ArrayList<Float>(genreCounts.values)
                    datalist.add(BarChartData(genreCountsList,"Added Songs By Genre",genreNames))
                } else {
                    // Handle different HTTP status codes
                }
            }

            override fun onFailure(call: Call<Map<String, Float>>, t: Throwable) {
                // Handle network or other exceptions
            }
        })
        val callfortempo = RetrofitClient.apiService.getEntityCount(entity = "tempos")
        callfortempo.enqueue(object : Callback<Map<String, Float>> {
            override fun onResponse(call: Call<Map<String, Float>>, response: Response<Map<String, Float>>) {
                if (response.isSuccessful) {
                    val genreCounts = response.body() ?: emptyMap()
                    // Handle successful response, e.g., update UI with genre counts
                    val genreNames = ArrayList<String>(genreCounts.keys)
                    val genreCountsList = ArrayList<Float>(genreCounts.values)
                    datalist.add(BarChartData(genreCountsList,"Added Songs By Tempo",genreNames))
                } else {
                    // Handle different HTTP status codes
                }
            }

            override fun onFailure(call: Call<Map<String, Float>>, t: Throwable) {
                // Handle network or other exceptions
            }
        })
        val callforartist = RetrofitClient.apiService.getEntityCount(entity = "artists")
        callforartist.enqueue(object : Callback<Map<String, Float>> {
            override fun onResponse(call: Call<Map<String, Float>>, response: Response<Map<String, Float>>) {
                if (response.isSuccessful) {
                    val genreCounts = response.body() ?: emptyMap()
                    // Handle successful response, e.g., update UI with genre counts
                    val genreNames = ArrayList<String>(genreCounts.keys)
                    val genreCountsList = ArrayList<Float>(genreCounts.values)
                    datalist.add(BarChartData(genreCountsList,"Added Songs By Artist",genreNames))
                } else {
                    // Handle different HTTP status codes
                }
            }

            override fun onFailure(call: Call<Map<String, Float>>, t: Throwable) {
                // Handle network or other exceptions
            }
        })
        val callformoods = RetrofitClient.apiService.getEntityCount(entity = "moods")
        callformoods.enqueue(object : Callback<Map<String, Float>> {
            override fun onResponse(call: Call<Map<String, Float>>, response: Response<Map<String, Float>>) {
                if (response.isSuccessful) {
                    val genreCounts = response.body() ?: emptyMap()
                    // Handle successful response, e.g., update UI with genre counts
                    val genreNames = ArrayList<String>(genreCounts.keys)
                    val genreCountsList = ArrayList<Float>(genreCounts.values)
                    datalist.add(BarChartData(genreCountsList,"Added Songs By Mood",genreNames))
                } else {
                    // Handle different HTTP status codes
                }
            }

            override fun onFailure(call: Call<Map<String, Float>>, t: Throwable) {
                // Handle network or other exceptions
            }
        })
    }
}