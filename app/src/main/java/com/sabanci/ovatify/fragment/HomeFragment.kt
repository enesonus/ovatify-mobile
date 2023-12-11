package com.sabanci.ovatify.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.adapter.HomeAdapter2
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.RecentlyAddedSongsReturn
import com.sabanci.ovatify.data.RecommendationReturnYouMightLike
import com.sabanci.ovatify.data.RecommendationSongYouMightLike
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.model.MusicModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment(R.layout.home_fragment2) {
    private lateinit var adapter: HomeAdapter2
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
    private lateinit var favoriteSongsList: ArrayList<Songs>
    private lateinit var musicModelListFavorites: ArrayList<MusicModel>
    private lateinit var musicModelListRecents: ArrayList<MusicModel>
    private lateinit var recentlyAddedSongsList: ArrayList<Songs>
    private lateinit var songCollections: ArrayList<LibraryModel>
    private lateinit var youMightLikeList: ArrayList<RecommendationSongYouMightLike>
    private lateinit var musicModelFriendRecommend: ArrayList<MusicModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist=ArrayList()
        songCollections = arrayListOf()
        datainitialize()
        val layoutManager=LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        //getFavoriteSongs(10)
        getNewlyAddedSongs(5)
        getRecommendYouMightLike(2)

        Log.d("Song Collections in Home Fragment Main Thread: ", songCollections.toString())


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
        recyclerView.adapter?.notifyDataSetChanged()





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


                    songCollections += LibraryModel("Empty" , musicModelListFavorites)
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

    private fun getNewlyAddedSongs(numberOfSongs: Int)
    {
        val callNewlyAddedSongs = RetrofitClient.apiService.getNewlyAddedSongs(numberOfSongs)

        Log.e("retrofit","retrofit favorite songs initial call okay")

        callNewlyAddedSongs.enqueue(object : Callback<RecentlyAddedSongsReturn> {
            override fun onResponse(
                call: Call<RecentlyAddedSongsReturn>,
                response: Response<RecentlyAddedSongsReturn>
            ) {
                Log.d("response tag", "${response}")

                if (response.isSuccessful)
                {
                    Log.e("favoriteSongsReturn","favorite songs return after list before: ")

                    val newlyAddedSongsReturn: RecentlyAddedSongsReturn? = response.body()

                    //Log.e("favoriteSongsReturn","favorite songs return after list : ${favoriteSongsReturn}")
                    Log.d("response body", "${response.body()}")


                    favoriteSongsList = arrayListOf()
                    musicModelListFavorites = arrayListOf()




                    if (newlyAddedSongsReturn != null)
                    {
                        favoriteSongsList = newlyAddedSongsReturn.songs
                        Log.d("favoriteSongsList", "${newlyAddedSongsReturn.songs}")
                        for (song in favoriteSongsList)
                        {
                            musicModelListFavorites += MusicModel(song.id, song.img_url, song.name, song.main_artist)
                        }
                    }


                    songCollections += LibraryModel("Empty" , musicModelListFavorites)
                    songCollections += LibraryModel("Newly Added" , musicModelListFavorites)
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

            override fun onFailure(call: Call<RecentlyAddedSongsReturn>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }
        )
    }

    private fun getRecommendYouMightLike(numberOfSongs: Int)
    {
        val callRecommendYouMight = RetrofitClient.apiService.getRecommendYouMightLike(numberOfSongs)

        callRecommendYouMight.enqueue(object : Callback<RecommendationReturnYouMightLike>{
            override fun onResponse(
                call: Call<RecommendationReturnYouMightLike>,
                response: Response<RecommendationReturnYouMightLike>
            ) {
                Log.d("Recommend Friend Listen", response.code().toString())
                Log.d("Recommend Friend Response Code", response.isSuccessful.toString())
                if (response.isSuccessful)
                {


                    val recommendYouMight : RecommendationReturnYouMightLike? = response.body()


                    Log.d("Recommend Friend Listen", recommendYouMight.toString())

                    youMightLikeList = arrayListOf()
                    musicModelFriendRecommend = arrayListOf()


                    if (recommendYouMight != null)
                    {
                        youMightLikeList = recommendYouMight.tracks_info

                        for (song in youMightLikeList)
                        {

                            Log.d("Strange List Error", song.main_artist.toString())
                            musicModelFriendRecommend += MusicModel(song.id, song.img_url, song.name, song.main_artist)


                        }
                    }


                    //songCollections += LibraryModel("You Might Like", musicModelFriendRecommend)
                    Log.d("explore song collections", songCollections.toString())


                    val music1photo=view?.findViewById<ImageView>(R.id.music1photo)
                    val music1name=view?.findViewById<TextView>(R.id.music1name)
                    val music1artist=view?.findViewById<TextView>(R.id.music1artist)
                    val music2photo=view?.findViewById<ImageView>(R.id.homeMusicPhoto2)
                    val music2name=view?.findViewById<TextView>(R.id.homeMusicName2)
                    val music2artist=view?.findViewById<TextView>(R.id.homeMusicArtist2)

                    if(musicModelFriendRecommend.size==2) {


                        Log.d("Music1 photo", music1photo.toString())

                        if (music1photo != null) {
                            music1photo.load(musicModelFriendRecommend[0].imageUrl)
                            Log.d("Music1 photo", "Photo changed")
                        }

                        if (music1name != null) {
                            music1name.text = musicModelFriendRecommend[0].songName
                        }

                        if (music1artist != null) {
                            music1artist.text = musicModelFriendRecommend[0].artistName
                        }

                        if (music2photo != null) {
                            music2photo.load(musicModelFriendRecommend[1].imageUrl)
                        }

                        if (music2name != null) {
                            music2name.text = musicModelFriendRecommend[1].songName
                        }

                        if (music2artist != null) {
                            music2artist.text = musicModelFriendRecommend[1].artistName
                        }

                        val song1 = view?.findViewById<ConstraintLayout>(R.id.homeSong1)
                        if (song1 != null) {
                            song1.setOnClickListener {
                                val intent = Intent(requireContext(), ShowMusicActivity::class.java)
                                intent.putExtra("song", musicModelFriendRecommend[0].id)
                                startActivity(intent)
                            }
                        }

                        val song2 = view?.findViewById<ConstraintLayout>(R.id.homeSong2)
                        if (song2 != null) {
                            song2.setOnClickListener {
                                val intent = Intent(requireContext(), ShowMusicActivity::class.java)
                                intent.putExtra("song", musicModelFriendRecommend[1].id)
                                startActivity(intent)
                            }
                        }
                    }
                    else{
                        val song1 = view?.findViewById<ConstraintLayout>(R.id.homeSong1)
                        val song2 = view?.findViewById<ConstraintLayout>(R.id.homeSong2)
                        song1?.visibility=View.INVISIBLE
                        song2?.visibility=View.INVISIBLE
                        val text=view?.findViewById<TextView>(R.id.textView14)
                        text?.visibility=View.VISIBLE
                    }



                    recyclerView.adapter?.notifyDataSetChanged()


                }

                else if (response.code() == 404)
                {
                    //songCollections += LibraryModel("You Might Like", ArrayList<MusicModel>())
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<RecommendationReturnYouMightLike>, t: Throwable) {
                Log.e("ExploreFragment", "Network call failed", t)
                // Optionally, show a message to the user
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun datainitialize(){
        //no data is initialized just some space
        for(i in 1..20){
            datalist.add("")
        }
    }
}