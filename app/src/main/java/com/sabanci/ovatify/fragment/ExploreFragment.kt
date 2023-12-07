package com.sabanci.ovatify.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.UploadActivity
import com.sabanci.ovatify.VerticalMusicActivity
import com.sabanci.ovatify.adapter.HomeAdapter
import com.sabanci.ovatify.adapter.LibraryAdapter2
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.RecentlyAddedSongsReturn
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.databinding.MusicListBinding
import com.sabanci.ovatify.databinding.MusicParentItemBinding
import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.model.MusicModel
import com.sabanci.ovatify.utils.SampleData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment:Fragment(R.layout.explore_music_list_fragment) {

    /*
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

     */


    private lateinit var favoriteSongsList: ArrayList<Songs>
    private lateinit var musicModelListFavorites: ArrayList<MusicModel>
    private lateinit var musicModelListRecents: ArrayList<MusicModel>
    private lateinit var recentlyAddedSongsList: ArrayList<Songs>
    private lateinit var songCollections: ArrayList<LibraryModel>
    //private lateinit var adapter: LibraryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //val rootView = inflater.inflate(R.layout.music_list, container, false)

        songCollections = arrayListOf()
        /*

        val myButton = view.findViewById<Button>(R.id.button2)
        myButton.setOnClickListener {
            val intent = Intent(requireActivity(), UploadActivity::class.java)
            startActivity(intent)

        }

         */

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
                    recyclerView.adapter?.notifyDataSetChanged()

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




        Thread.sleep(100)
        val callRecentlyAddedSongsReturn = RetrofitClient.apiService.getRecentlyAddedSongs("5")
        callRecentlyAddedSongsReturn.enqueue(object : Callback<RecentlyAddedSongsReturn> {
            override fun onResponse(
                call: Call<RecentlyAddedSongsReturn>,
                response: Response<RecentlyAddedSongsReturn>
            ) {
                if (response.isSuccessful)
                {
                    Log.d("response log","recently added songs response is successful")

                    val recentlyAddedSongsReturn: RecentlyAddedSongsReturn? = response.body()



                    musicModelListRecents = arrayListOf()
                    recentlyAddedSongsList = arrayListOf()


                    if (recentlyAddedSongsReturn != null)
                    {

                        recentlyAddedSongsList = recentlyAddedSongsReturn.songs
                        for (song in recentlyAddedSongsList)
                        {
                            musicModelListRecents += MusicModel(song.id, song.img_url, song.name, song.main_artist)
                        }
                    }

                    songCollections += LibraryModel("Recently Added Songs" , musicModelListRecents)
                    recyclerView.adapter?.notifyDataSetChanged()


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




        Log.d("collections comparison", "SampleData.collections: ${SampleData.collections}")
        Log.d("collections comparison", "songCollections: ${songCollections}")

        recyclerView = view.findViewById(R.id.exploreRecView)
        recyclerView.adapter=LibraryAdapter2(songCollections, object : IhomeclickListener {
            override fun onItemClick(position: Int) {
                Log.d("Sent Intent", "${songCollections[position]}")
                val clickedItem = songCollections[position]
                val intent = Intent(requireContext(), VerticalMusicActivity::class.java)
                intent.putExtra("List Title", songCollections[position].title)
                startActivity(intent)
                Toast.makeText(requireContext(), "Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
            }

        })

        recyclerView.adapter?.notifyDataSetChanged()

        //return rootView
    }



}