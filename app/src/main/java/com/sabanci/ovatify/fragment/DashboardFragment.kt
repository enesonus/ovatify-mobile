package com.sabanci.ovatify.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
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
import com.sabanci.ovatify.data.ChartData
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.LineChartData
import com.sabanci.ovatify.data.SongCounts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.github.mikephil.charting.charts.BarChart

class DashboardFragment:Fragment(R.layout.dashboard_fragment) {
    private lateinit var adapter: DashboardAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<ChartData> //data is not initialized yet
    private lateinit var prbar:ProgressBar
    private var tracker=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist=ArrayList()
        prbar=view.findViewById<ProgressBar>(R.id.progressBardashboard)
        datainitialize()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.dashboard_recview)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= DashboardAdapter(datalist)
        recyclerView.adapter=adapter
    }
    private fun datainitialize(){
        prbar.visibility=View.VISIBLE
        val callforgenre = RetrofitClient.apiService.getEntityCount(entity = "genres")
        callforgenre.enqueue(object : Callback<Map<String, Float>> {
            override fun onResponse(call: Call<Map<String, Float>>, response: Response<Map<String, Float>>) {
                if (response.isSuccessful) {
                    val genreCounts = response.body() ?: emptyMap()
                    // Handle successful response, e.g., update UI with genre counts
                    val genreNames = ArrayList<String>(genreCounts.keys)
                    val genreCountsList = ArrayList<Float>(genreCounts.values)

                    datalist.add(ChartData(BarChartData(ArrayList(genreCountsList.subList(0, minOf(5, genreCountsList.size))),"Added Songs By Genre",ArrayList(genreNames.subList(0, minOf(5, genreNames.size)))),null))
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
                    datalist.add(ChartData(BarChartData(ArrayList(genreCountsList.subList(0, minOf(5, genreCountsList.size))),"Added Songs By Tempo",ArrayList(genreNames.subList(0, minOf(5, genreNames.size)))),null))
                    trackForBar()
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

                    datalist.add(ChartData(BarChartData(ArrayList(genreCountsList.subList(0, minOf(5, genreCountsList.size))),"Added Songs By Artist",ArrayList(genreNames.subList(0, minOf(5, genreNames.size)))),null))
                    trackForBar()
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
                    datalist.add(ChartData(BarChartData(ArrayList(genreCountsList.subList(0, minOf(5, genreCountsList.size))),"Added Songs By Mood",ArrayList(genreNames.subList(0, minOf(5, genreNames.size)))),null))
                    trackForBar()
                } else {
                    // Handle different HTTP status codes
                }
            }

            override fun onFailure(call: Call<Map<String, Float>>, t: Throwable) {
                // Handle network or other exceptions
            }
        })
        val callforrecentaddition=RetrofitClient.apiService.getRecentAdditionCounts()
        callforrecentaddition.enqueue(object:Callback<SongCounts>{
            override fun onResponse(call: Call<SongCounts>, response: Response<SongCounts>) {
              if(response.isSuccessful){
                  val genreCounts=response.body()
                  val dates = ArrayList<String>()
                  val counts = ArrayList<Int>()
                  if (genreCounts != null) {
                      for (dateAndCount in genreCounts.song_counts) {
                          dates.add(dateAndCount.date)
                          counts.add(dateAndCount.count)
                      }
                  }
                  datalist.add(ChartData(null, LineChartData(counts,"Recent Additions per Day",dates)))
                  trackForBar()
              }
            }

            override fun onFailure(call: Call<SongCounts>, t: Throwable) {
                Log.e("apicall"," recent failed")
            }
        })

    }
    private fun trackForBar(){
        tracker++
        if(tracker==4){
            prbar.visibility=View.INVISIBLE
            adapter.notifyDataSetChanged()
        }
    }
}