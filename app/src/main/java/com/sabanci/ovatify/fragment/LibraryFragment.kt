package com.sabanci.ovatify.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.UploadActivity
import com.sabanci.ovatify.VerticalMusicActivity
import com.sabanci.ovatify.adapter.LibraryAdapter
import com.sabanci.ovatify.adapter.LibraryAdapter2
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.RecentlyAddedSongsReturn
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.model.MusicModel
import com.sabanci.ovatify.utils.SampleData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections

class LibraryFragment:Fragment(R.layout.music_list) {
    private lateinit var favoriteSongsList: List<Songs>
    private lateinit var musicModelListFavorites: List<MusicModel>
    private lateinit var musicModelListRecents: List<MusicModel>
    private lateinit var recentlyAddedSongsList: List<Songs>
    private lateinit var songCollections: List<LibraryModel>
    private lateinit var adapter: LibraryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val rootView = inflater.inflate(R.layout.music_list, container, false)

        songCollections = mutableListOf()

        val myButton = view.findViewById<Button>(R.id.button2)
        myButton.setOnClickListener {
            val intent = Intent(requireActivity(), UploadActivity::class.java)
            startActivity(intent)

        }

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

                    Log.e("favoriteSongsReturn","favorite songs return after list : ${favoriteSongsReturn}")
                    Log.d("response tag", "${response.body()}")

                    favoriteSongsList = mutableListOf()
                    musicModelListFavorites = mutableListOf()
/*
                    if (favoriteSongsReturn != null)
                    {
                        favoriteSongsList = favoriteSongsReturn.results
                        for (song in favoriteSongsList)
                        {
                            musicModelListFavorites += MusicModel(song.img_url)
                        }
                    }

                    songCollections += LibraryModel("Your Favorites" , musicModelListFavorites)

 */

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






        val callRecentlyAddedSongsReturn = RetrofitClient.apiService.getRecentlyAddedSongs("5")
        callRecentlyAddedSongsReturn.enqueue(object : Callback<RecentlyAddedSongsReturn> {
            override fun onResponse(
                call: Call<RecentlyAddedSongsReturn>,
                response: Response<RecentlyAddedSongsReturn>
            ) {
                if (response.isSuccessful)
                {
                    Log.e("response log","recently added songs response is successful")

                    val recentlyAddedSongsReturn: RecentlyAddedSongsReturn? = response.body()

                    recentlyAddedSongsList = mutableListOf()
                    musicModelListRecents = mutableListOf()

                    /*
                    if (recentlyAddedSongsReturn != null)
                    {
                        recentlyAddedSongsList = recentlyAddedSongsReturn.results
                        for (song in recentlyAddedSongsList)
                        {
                            musicModelListRecents += MusicModel(song.img_url)
                        }
                    }

                    songCollections += LibraryModel("Recently Added Songs" , musicModelListRecents)

                     */
                }

                else
                {
                    Log.e("response log","recently added songs response is not successful ${response.code()}")

                }
            }

            override fun onFailure(call: Call<RecentlyAddedSongsReturn>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }



        )



        recyclerView = view.findViewById(R.id.library_recycler_view)
        recyclerView.adapter=LibraryAdapter2(SampleData.collections, object : IhomeclickListener{
            override fun onItemClick(position: Int) {
                val clickedItem = SampleData.collections[position]
                val intent = Intent(requireContext(), VerticalMusicActivity::class.java)
                //    intent.putExtra("key", "value") will implement
                startActivity(intent)
                Toast.makeText(requireContext(), "Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
            }

        })

        //return rootView
    }






}