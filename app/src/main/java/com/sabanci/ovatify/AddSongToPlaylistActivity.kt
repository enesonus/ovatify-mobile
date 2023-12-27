package com.sabanci.ovatify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanci.ovatify.adapter.AddToSongPlaylistAdapter
import com.sabanci.ovatify.adapter.SongPlaylistAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.DatabaseSearchReturn
import com.sabanci.ovatify.data.DatabaseSong
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.databinding.AddSongToPlaylistBinding
import com.sabanci.ovatify.databinding.ShowPlaylistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSongToPlaylistActivity:AppCompatActivity() {
    lateinit var id:String
    lateinit var binding : AddSongToPlaylistBinding
    lateinit var songslist:ArrayList<Songs>
    lateinit var adapter: AddToSongPlaylistAdapter
    private val apiCallHandler = Handler(Looper.getMainLooper())
    private val apiCallDelayMillis = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=AddSongToPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id =intent.getStringExtra("id").toString()
        songslist= arrayListOf()
        val layoutManager= LinearLayoutManager(this)
        binding.recviewAddToPlaylist.layoutManager=layoutManager
        binding.recviewAddToPlaylist.setHasFixedSize(true)
        binding.searchBox.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                apiCallHandler.removeCallbacksAndMessages(null)

                // Post a delayed callback to make the API call after a specified delay
                apiCallHandler.postDelayed({
                    val newText = s.toString()
                    makeApiCall(newText)
                }, apiCallDelayMillis)
            }
        })


    }
    private fun makeApiCall(txt: String){
        val call=RetrofitClient.apiService.searchDatabase(txt)
        call.enqueue(object: Callback<DatabaseSearchReturn> {
            override fun onResponse(
                call: Call<DatabaseSearchReturn>,
                response: Response<DatabaseSearchReturn>
            ) {
                val apireturn=response.body()
                if (apireturn != null) {
                    songslist=convertToSongs(apireturn.songs_info)
                    adapter= AddToSongPlaylistAdapter(songslist,id,object : IhomeclickListener {
                        override fun onItemClick(position: Int) {
                            val clickedItem=songslist[position]
                                val intent= Intent(this@AddSongToPlaylistActivity,ShowMusicActivity::class.java)
                                intent.putExtra("song",songslist[position].id)
                                startActivity(intent)
                        }
                    })
                    binding.recviewAddToPlaylist.adapter=adapter
                }
            }


            override fun onFailure(call: Call<DatabaseSearchReturn>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun convertToSongs(databaseSongs: List<DatabaseSong>): ArrayList<Songs> {
        return ArrayList(databaseSongs.map { databaseSong ->
            Songs(
                id = databaseSong.spotify_id,
                name = databaseSong.track_name,
                release_year = databaseSong.release_year.toString(),
                main_artist = databaseSong.artist.firstOrNull() ?: "",
                img_url = databaseSong.album_url
            )
        })
    }
}