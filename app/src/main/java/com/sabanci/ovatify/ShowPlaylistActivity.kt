package com.sabanci.ovatify

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.sabanci.ovatify.adapter.PlaylistAdapter
import com.sabanci.ovatify.adapter.SongPlaylistAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.PlaylistByIdReturn
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.databinding.ShowPlaylistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowPlaylistActivity:AppCompatActivity() {
    lateinit var id:String
    lateinit var binding : ShowPlaylistBinding
    lateinit var songsInPlaylist:ArrayList<Songs>
    lateinit var adapter: SongPlaylistAdapter
    var isEditButtonclicked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ShowPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id =intent.getStringExtra("id").toString()
        songsInPlaylist= arrayListOf()
        val layoutManager= LinearLayoutManager(this)
        binding.recviewPlaylistSong.layoutManager=layoutManager
        binding.recviewPlaylistSong.setHasFixedSize(true)
        binding.backButton.setOnClickListener{
            onBackPressed()
        }
        binding.CancelButton.setOnClickListener {
            binding.addSongButton.visibility=View.VISIBLE
            binding.CancelButton.visibility=View.INVISIBLE
            binding.EditText.text = "Edit"
            isEditButtonclicked=false
            binding.playlistname.isEnabled = false
            binding.playlistdescrp.isEnabled = false
        }
        binding.addSongButton.setOnClickListener{
            val intent=Intent(this,AddSongToPlaylistActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        binding.EditButton.setOnClickListener {
            if(!isEditButtonclicked) {
                binding.addSongButton.visibility=View.INVISIBLE
                binding.CancelButton.visibility=View.VISIBLE
                isEditButtonclicked = true
                binding.playlistname.isEnabled = true
                binding.playlistdescrp.isEnabled = true
                binding.EditText.text = "Save"
                Toast.makeText(this, "Please click name or description to edit", Toast.LENGTH_SHORT)
                    .show()
            }
                else {
                val  call =RetrofitClient.apiService.editPlaylist(mapOf("playlist_id" to id, "name" to binding.playlistname.text.toString(),"description" to binding.playlistdescrp.text.toString() ))
                call.enqueue(object:Callback<RResponse>{
                    override fun onResponse(call: Call<RResponse>, response: Response<RResponse>) {
                        if(response.isSuccessful){
                            binding.addSongButton.visibility=View.VISIBLE
                            binding.CancelButton.visibility=View.INVISIBLE
                            Toast.makeText(this@ShowPlaylistActivity,"Edited succesfully",Toast.LENGTH_SHORT)
                            binding.EditText.text = "Edit"
                            isEditButtonclicked=false
                            binding.playlistname.isEnabled = false
                            binding.playlistdescrp.isEnabled = false
                        }
                        else{
                            Log.e("api call returned",response.errorBody().toString())
                        }
                    }
                    override fun onFailure(call: Call<RResponse>, t: Throwable) {
                        Log.e("api call did not return","problem")
                    }
                })
            }
        }
        getData()

    }
    private fun getData(){
        binding.prbarPlaylisTinfo.visibility=View.VISIBLE
        val call= id?.let { RetrofitClient.apiService.getPlaylistById(it) }
        call?.enqueue(object:Callback<PlaylistByIdReturn>{
            override fun onResponse(
                call: Call<PlaylistByIdReturn>,
                response: Response<PlaylistByIdReturn>
            ) {
                if(response.isSuccessful){
                    val apireturn=response.body()
                    val playlist= apireturn!!.playlist
                    binding.prbarPlaylisTinfo.visibility=View.INVISIBLE
                    binding.playlist2.visibility=View.VISIBLE
                    binding.recviewPlaylistSong.visibility=View.VISIBLE
                    binding.playlistname.setText(playlist.name)
                    binding.playlistdescrp.setText(playlist.description)
                    songsInPlaylist=playlist.songs
                    adapter= SongPlaylistAdapter(songsInPlaylist,id,object :IhomeclickListener{
                        override fun onItemClick(position: Int) {
                            val clickedItem=songsInPlaylist[position]
                            val intent= Intent(this@ShowPlaylistActivity,ShowMusicActivity::class.java)
                            intent.putExtra("song",songsInPlaylist[position].id)
                            startActivity(intent)
                        }
                    })
                    binding.recviewPlaylistSong.adapter=adapter
                    if (playlist.songs.isNotEmpty()) {
                        if (playlist.songs.size >= 1) {
                            binding.playlistPhoto1.load(playlist.songs[0].img_url)
                            if (playlist.songs.size >=2) {
                                binding.playlistPhoto2.load(playlist.songs[1].img_url)
                                if (playlist.songs.size>=3) {
                                    binding.playlistPhoto3.load(playlist.songs[2].img_url)
                                    if (playlist.songs.size>=4) {
                                        binding.playlistPhoto4.load(playlist.songs[3].img_url)
                                    } else {
                                        binding.playlistPhoto4.load(playlist.songs[2].img_url)
                                    }
                                } else {
                                    binding.playlistPhoto3.load(playlist.songs[1].img_url)
                                    binding.playlistPhoto4.load(playlist.songs[1].img_url)
                                }
                            } else {
                                binding.playlistPhoto2.load(playlist.songs[0].img_url)
                                binding.playlistPhoto3.load(playlist.songs[0].img_url)
                                binding.playlistPhoto4.load(playlist.songs[0].img_url)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PlaylistByIdReturn>, t: Throwable) {
                Log.e("api call","failed")
            }

        })
    }
}