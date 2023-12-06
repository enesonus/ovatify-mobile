package com.sabanci.ovatify.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.adapter.HomeAdapter
import com.sabanci.ovatify.adapter.HomeAdapter2
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.model.MusicModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment(R.layout.home_fragment) {
    private lateinit var adapter: HomeAdapter2
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
    private lateinit var favoriteSongsList: ArrayList<Songs>
    private lateinit var musicModelListFavorites: ArrayList<MusicModel>
    private lateinit var musicModelListRecents: ArrayList<MusicModel>
    private lateinit var recentlyAddedSongsList: ArrayList<Songs>
    private lateinit var songCollections: ArrayList<LibraryModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist=ArrayList()
        songCollections = arrayListOf()
        datainitialize()
        val layoutManager=LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.recview)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        getFavoriteSongs(10)

        Log.d("Song Collections in Home Fragment Main Thread: ", songCollections.toString())

        /*
        adapter= HomeAdapter2(songCollections,object : IhomeclickListener {
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

         */
    }

    private fun getFavoriteSongs(numberOfSongs : Int)
    {
        val callFavoriteSongsReturn = RetrofitClient.apiService.getFavoriteSongs("5")

        Log.e("retrofit","retrofit favorite songs initial call okay")

        callFavoriteSongsReturn.enqueue(object : Callback<FavoriteSongsReturn> {
            override fun onResponse(
                call: Call<FavoriteSongsReturn>,
                response: Response<FavoriteSongsReturn>
            ) {
                Log.d("response tag", "${response}")

                if (response.isSuccessful)
                {
                    Log.e("favoriteSongsReturn","favorite songs return after list before: ")

                    val favoriteSongsReturn: FavoriteSongsReturn? = response.body()

                    //Log.e("favoriteSongsReturn","favorite songs return after list : ${favoriteSongsReturn}")
                    Log.d("response body", "${response.body()}")


                    favoriteSongsList = arrayListOf()
                    musicModelListFavorites = arrayListOf()




                    if (favoriteSongsReturn != null)
                    {
                        favoriteSongsList = favoriteSongsReturn.songs
                        Log.d("favoriteSongsList", "${favoriteSongsReturn.songs}")
                        for (song in favoriteSongsList)
                        {
                            musicModelListFavorites += MusicModel(song.id, song.img_url, song.name, song.main_artist)
                        }
                    }

                    songCollections += LibraryModel("Your Favorites" , musicModelListFavorites)
                    //recyclerView.adapter?.notifyDataSetChanged()

                    adapter= HomeAdapter2(songCollections,object : IhomeclickListener {
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


                    Log.d("songCollections1", "songCollections: ${songCollections}")

                }

                else
                {
                    Log.e("response log","favorite songs response is not successful ${response.code()}")

                }
            }

            override fun onFailure(call: Call<FavoriteSongsReturn>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }
        )
    }
    private fun datainitialize(){
        //no data is initialized just some space
        for(i in 1..20){
            datalist.add("")
        }
    }
}