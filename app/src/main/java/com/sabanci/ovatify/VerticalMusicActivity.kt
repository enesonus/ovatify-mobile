package com.sabanci.ovatify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.adapter.VerticalSongListAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.RecentlyAddedSongsReturn
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.databinding.VerticalSongsListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerticalMusicActivity:AppCompatActivity() {

    private lateinit var favoriteSongsList: ArrayList<Songs>
    private lateinit var recentlyAddedSongsList: ArrayList<Songs>
    private lateinit var recyclerView: RecyclerView
    private var songList: ArrayList<Songs> = arrayListOf()
    private lateinit var verticalSongsListAdapter: VerticalSongListAdapter
    private lateinit var binding: VerticalSongsListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vertical_songs_list)

        recyclerView = findViewById(R.id.verticalSongsRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var listName = intent.getStringExtra("List Title")
        Log.d("Intent Output", "listName variable: ${listName}")

        findViewById<TextView>(R.id.Music_info_vertical).text = listName

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        if (listName == "Your Favorites")
        {
            val callFavoriteSongsReturn = RetrofitClient.apiService.getFavoriteSongs("5")
            callFavoriteSongsReturn.enqueue(object : Callback<FavoriteSongsReturn> {
                override fun onResponse(
                    call: Call<FavoriteSongsReturn>,
                    response: Response<FavoriteSongsReturn>
                ) {


                    if (response.isSuccessful)
                    {

                        val favoriteSongsReturn: FavoriteSongsReturn? = response.body()

                        favoriteSongsList = arrayListOf()


                        if (favoriteSongsReturn != null)
                        {
                            favoriteSongsList = favoriteSongsReturn.songs
                            Log.d("favoriteSongsList", "${favoriteSongsReturn.songs}")

                        }

                        songList = favoriteSongsList
                        Log.d("songList in Vertical Music1", "songList: ${songList}")

                        //recyclerView.adapter?.notifyDataSetChanged()

                        verticalSongsListAdapter = VerticalSongListAdapter(songList, object :
                            IhomeclickListener {
                            override fun onItemClick(position: Int) {
                                // Handle item click here
                                val clickedItem = songList[position]
                                val intent = Intent(this@VerticalMusicActivity, ShowMusicActivity::class.java)
                                intent.putExtra("song", songList[position].id)
                                startActivity(intent)
                                Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })

                        recyclerView.adapter = verticalSongsListAdapter

                    }

                    else
                    {

                    }
                }

                override fun onFailure(call: Call<FavoriteSongsReturn>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
        }

        else if (listName == "Recently Added Songs")
        {
            val callRecentlyAddedSongsReturn = RetrofitClient.apiService.getRecentlyAddedSongs("5")
            callRecentlyAddedSongsReturn.enqueue(object : Callback<RecentlyAddedSongsReturn> {
                override fun onResponse(
                    call: Call<RecentlyAddedSongsReturn>,
                    response: Response<RecentlyAddedSongsReturn>
                ) {
                    if (response.isSuccessful)
                    {

                        val recentlyAddedSongsReturn: RecentlyAddedSongsReturn? = response.body()

                        recentlyAddedSongsList = arrayListOf()

                        if (recentlyAddedSongsReturn != null)
                        {

                            recentlyAddedSongsList = recentlyAddedSongsReturn.songs

                        }

                        songList = recentlyAddedSongsList

                        //recyclerView.adapter?.notifyDataSetChanged()

                        verticalSongsListAdapter = VerticalSongListAdapter(songList, object : IhomeclickListener {
                            override fun onItemClick(position: Int) {
                                // Handle item click here
                                Log.d("Item Position", "${songList[position]}")
                                val clickedItem = songList[position]
                                val intent = Intent(this@VerticalMusicActivity, ShowMusicActivity::class.java)
                                intent.putExtra("song", songList[position].id)
                                startActivity(intent)
                                Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })
                        recyclerView.adapter = verticalSongsListAdapter

                    }


                    else
                    {

                    }
                }

                override fun onFailure(call: Call<RecentlyAddedSongsReturn>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
        }



        Log.d("songList in Vertical Music2", "songList: ${songList}")
        //verticalSongsListAdapter = VerticalSongListAdapter(songList)
        //recyclerView.adapter = verticalSongsListAdapter


    }


}