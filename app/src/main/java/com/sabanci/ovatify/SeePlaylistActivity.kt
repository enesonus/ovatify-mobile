package com.sabanci.ovatify

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sabanci.ovatify.adapter.DashboardAdapter
import com.sabanci.ovatify.adapter.PlaylistAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.AllPlaylistsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.Playlistitem
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.databinding.SeePlaylistsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeePlaylistActivity:AppCompatActivity() {
    private lateinit var binding:SeePlaylistsBinding
    private lateinit var datalist:ArrayList<Playlistitem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=SeePlaylistsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            val intent=Intent(this,HomePageActivity::class.java)
            startActivity(intent)
        }

        binding.createAPlaylist.setOnClickListener {
            createAPlaylist()
        }
        datalist= arrayListOf()
        getPlaylist()
        val layoutManager= LinearLayoutManager(this)
        binding.recviewplaylist.layoutManager=layoutManager
        binding.recviewplaylist.setHasFixedSize(true)
    }
    private fun createAPlaylist(){
        val callcreate=RetrofitClient.apiService.createEmptyPlaylist(mapOf("name" to "NewPlaylist","description" to "This is a new playlist of yours" ))
        callcreate.enqueue(object:Callback<RResponse>{
            override fun onResponse(call: Call<RResponse>, response: Response<RResponse>) {
                if(response.isSuccessful){
                        val intent = Intent(this@SeePlaylistActivity, SeePlaylistActivity::class.java)
                        startActivity(intent)
                }
                else {

                    runOnUiThread {

                            Toast.makeText(this@SeePlaylistActivity, "problem", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<RResponse>, t: Throwable) {

            }

        })
    }
    private fun getPlaylist(){
        binding.progressBar4.visibility=View.VISIBLE
        val callPlaylists= RetrofitClient.apiService.getPlaylists(10)
        callPlaylists.enqueue(object: Callback<AllPlaylistsReturn> {
            override fun onResponse(
                call: Call<AllPlaylistsReturn>,
                response: Response<AllPlaylistsReturn>
            ) {
                if(response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@SeePlaylistActivity, "Response succesfull", Toast.LENGTH_SHORT).show()
                    }
                    val data = response.body()
                    if (data != null) {
                        datalist=data.items
                        binding.progressBar4.visibility= View.INVISIBLE
                        binding.createAPlaylist.visibility=View.VISIBLE
                        binding.recviewplaylist.visibility=View.VISIBLE
                        val adapter= PlaylistAdapter(datalist,object :IhomeclickListener{
                            override fun onItemClick(position: Int) {
                                val clickedItem=datalist[position]
                                val intent =Intent(this@SeePlaylistActivity,ShowPlaylistActivity::class.java)
                                intent.putExtra("id",clickedItem.id)
                                startActivity(intent)
                            }
                        })
                        binding.recviewplaylist.adapter=adapter
                    }
                }
                else {
                    Log.e("api call","playlist wrong response")
                }
            }
            override fun onFailure(call: Call<AllPlaylistsReturn>, t: Throwable) {
                Log.e("api call","playlist no response")
            }

        })
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val intent=Intent(this,HomePageActivity::class.java)
        startActivity(intent)
    }
}