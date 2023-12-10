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
import com.sabanci.ovatify.data.RecommendationReturn
import com.sabanci.ovatify.data.RecommendationReturnYouMightLike
import com.sabanci.ovatify.data.RecommendationSinceYouLikeReturn
import com.sabanci.ovatify.data.RecommendationSongYouMightLike
import com.sabanci.ovatify.data.RecommendedSong
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.databinding.VerticalSongsListBinding
import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.model.MusicModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerticalMusicActivity:AppCompatActivity() {

    private lateinit var favoriteSongsList: ArrayList<Songs>
    private lateinit var recentlyAddedSongsList: ArrayList<Songs>
    private lateinit var newYouMightLikeList: ArrayList<Songs>
    private lateinit var friendListenList : ArrayList<RecommendedSong>
    private lateinit var youMightLikeList : ArrayList<RecommendationSongYouMightLike>
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
            val callFavoriteSongsReturn = RetrofitClient.apiService.getFavoriteSongs("500")
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
                                //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })

                        recyclerView.adapter = verticalSongsListAdapter
                        //recyclerView.adapter?.notifyDataSetChanged()

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
            val callRecentlyAddedSongsReturn = RetrofitClient.apiService.getRecentlyAddedSongs("500")
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
                                //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })
                        recyclerView.adapter = verticalSongsListAdapter
                        //recyclerView.adapter?.notifyDataSetChanged()

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

        else if (listName == "Your Friends Like")
        {
            val callFriendListenRecommend = RetrofitClient.apiService.getRecommendFriendListen(500)
            callFriendListenRecommend.enqueue(object : Callback<RecommendationReturn> {
                override fun onResponse(
                    call: Call<RecommendationReturn>,
                    response: Response<RecommendationReturn>
                ) {
                    if (response.isSuccessful)
                    {

                        val friendListenReturn: RecommendationReturn? = response.body()

                        friendListenList = arrayListOf()

                        if (friendListenReturn != null)
                        {

                            friendListenList = friendListenReturn.tracks_info

                        }




                        for (song in friendListenList)
                        {
                            if (song.main_artist.size == 0)
                            {
                                songList += Songs(song.id, song.name, song.release_year.toString(), "", song.img_url)
                            }

                            else
                            {
                                var artists = ""
                                for (artist in song.main_artist)
                                {
                                    artists += artist + ", "
                                }

                                artists = artists.substring(0, artists.length-2)
                                songList += Songs(song.id, song.name, song.release_year.toString(), artists, song.img_url)
                            }


                        }

                        //songList = friendListenList

                        //recyclerView.adapter?.notifyDataSetChanged()

                        verticalSongsListAdapter = VerticalSongListAdapter(songList, object : IhomeclickListener {
                            override fun onItemClick(position: Int) {
                                // Handle item click here
                                Log.d("Item Position", "${songList[position]}")
                                val clickedItem = songList[position]
                                val intent = Intent(this@VerticalMusicActivity, ShowMusicActivity::class.java)
                                intent.putExtra("song", songList[position].id)
                                startActivity(intent)
                                //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })
                        recyclerView.adapter = verticalSongsListAdapter
                        //recyclerView.adapter?.notifyDataSetChanged()

                    }


                    else
                    {

                    }
                }

                override fun onFailure(call: Call<RecommendationReturn>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
        }

        else if (listName == "Newly Added")
        {
            val callRecentlyAddedSongsReturn = RetrofitClient.apiService.getNewlyAddedSongs(500)
            callRecentlyAddedSongsReturn.enqueue(object : Callback<RecentlyAddedSongsReturn> {
                override fun onResponse(
                    call: Call<RecentlyAddedSongsReturn>,
                    response: Response<RecentlyAddedSongsReturn>
                ) {
                    if (response.isSuccessful)
                    {

                        val newlyAddedReturn: RecentlyAddedSongsReturn? = response.body()

                        recentlyAddedSongsList = arrayListOf()

                        if (newlyAddedReturn != null)
                        {

                            recentlyAddedSongsList = newlyAddedReturn.songs

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
                                //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })
                        recyclerView.adapter = verticalSongsListAdapter
                        //recyclerView.adapter?.notifyDataSetChanged()

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

        else if (listName == "You Might Like")
        {
            val callYouMightLike = RetrofitClient.apiService.getRecommendYouMightLike(99)
            callYouMightLike.enqueue(object : Callback<RecommendationReturnYouMightLike> {
                override fun onResponse(
                    call: Call<RecommendationReturnYouMightLike>,
                    response: Response<RecommendationReturnYouMightLike>
                ) {
                    Log.d("You Might Like Vertical", response.code().toString())

                    if (response.isSuccessful)
                    {

                        val newlyAddedReturn: RecommendationReturnYouMightLike? = response.body()

                        youMightLikeList = arrayListOf()

                        if (newlyAddedReturn != null)
                        {

                            youMightLikeList = newlyAddedReturn.tracks_info

                        }

                        newYouMightLikeList = arrayListOf()

                        for (song in youMightLikeList)
                        {
                            newYouMightLikeList += Songs(song.id, song.name, song.release_year.toString(), song.main_artist, song.img_url)
                        }

                        songList = newYouMightLikeList

                        //recyclerView.adapter?.notifyDataSetChanged()

                        verticalSongsListAdapter = VerticalSongListAdapter(songList, object : IhomeclickListener {
                            override fun onItemClick(position: Int) {
                                // Handle item click here
                                Log.d("Item Position", "${songList[position]}")
                                val clickedItem = songList[position]
                                val intent = Intent(this@VerticalMusicActivity, ShowMusicActivity::class.java)
                                intent.putExtra("song", songList[position].id)
                                startActivity(intent)
                                //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })
                        recyclerView.adapter = verticalSongsListAdapter
                        //recyclerView.adapter?.notifyDataSetChanged()

                    }


                    else
                    {

                    }
                }

                override fun onFailure(call: Call<RecommendationReturnYouMightLike>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
        }

        else if (listName == "Friend Mix")
        {
            val callYouMightLike = RetrofitClient.apiService.getRecommendFriendMix(99)
            callYouMightLike.enqueue(object : Callback<RecommendationReturnYouMightLike> {
                override fun onResponse(
                    call: Call<RecommendationReturnYouMightLike>,
                    response: Response<RecommendationReturnYouMightLike>
                ) {
                    Log.d("You Might Like Vertical", response.code().toString())

                    if (response.isSuccessful)
                    {

                        val newlyAddedReturn: RecommendationReturnYouMightLike? = response.body()

                        youMightLikeList = arrayListOf()

                        if (newlyAddedReturn != null)
                        {

                            youMightLikeList = newlyAddedReturn.tracks_info

                        }

                        newYouMightLikeList = arrayListOf()

                        for (song in youMightLikeList)
                        {
                            newYouMightLikeList += Songs(song.id, song.name, song.release_year.toString(), song.main_artist, song.img_url)
                        }

                        songList = newYouMightLikeList

                        //recyclerView.adapter?.notifyDataSetChanged()

                        verticalSongsListAdapter = VerticalSongListAdapter(songList, object : IhomeclickListener {
                            override fun onItemClick(position: Int) {
                                // Handle item click here
                                Log.d("Item Position", "${songList[position]}")
                                val clickedItem = songList[position]
                                val intent = Intent(this@VerticalMusicActivity, ShowMusicActivity::class.java)
                                intent.putExtra("song", songList[position].id)
                                startActivity(intent)
                                //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })
                        recyclerView.adapter = verticalSongsListAdapter
                        //recyclerView.adapter?.notifyDataSetChanged()

                    }


                    else
                    {

                    }
                }

                override fun onFailure(call: Call<RecommendationReturnYouMightLike>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
        }

        else
        {
            val callYouMightLike = RetrofitClient.apiService.getRecommendSinceYouLike(99)
            callYouMightLike.enqueue(object : Callback<RecommendationSinceYouLikeReturn> {
                override fun onResponse(
                    call: Call<RecommendationSinceYouLikeReturn>,
                    response: Response<RecommendationSinceYouLikeReturn>
                ) {
                    Log.d("You Might Like Vertical", response.code().toString())

                    if (response.isSuccessful)
                    {

                        val newlyAddedReturn: RecommendationSinceYouLikeReturn? = response.body()

                        youMightLikeList = arrayListOf()

                        if (newlyAddedReturn != null)
                        {
                            val categorizedSongs: Map<String, ArrayList<RecommendationSongYouMightLike>> = newlyAddedReturn.tracks_info
                            newYouMightLikeList = arrayListOf()

                            for ((categoryName, songs) in categorizedSongs)
                            {
                                if ("Since you like " + categoryName == listName)
                                {
                                    for (song in songs)
                                    {
                                        newYouMightLikeList += Songs(song.id, song.name, song.release_year.toString(), song.main_artist, song.img_url)
                                    }
                                }

                                else
                                {
                                    continue
                                }
                            }
                        }


                        songList = newYouMightLikeList

                        //recyclerView.adapter?.notifyDataSetChanged()

                        verticalSongsListAdapter = VerticalSongListAdapter(songList, object : IhomeclickListener {
                            override fun onItemClick(position: Int) {
                                // Handle item click here
                                Log.d("Item Position", "${songList[position]}")
                                val clickedItem = songList[position]
                                val intent = Intent(this@VerticalMusicActivity, ShowMusicActivity::class.java)
                                intent.putExtra("song", songList[position].id)
                                startActivity(intent)
                                //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                            }
                        })
                        recyclerView.adapter = verticalSongsListAdapter
                        //recyclerView.adapter?.notifyDataSetChanged()

                    }


                    else
                    {

                    }
                }

                override fun onFailure(call: Call<RecommendationSinceYouLikeReturn>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
            )
        }







        Log.d("songList in Vertical Music2", "songList: ${songList}")
        //verticalSongsListAdapter = VerticalSongListAdapter(songList)
        //recyclerView.adapter = verticalSongsListAdapter


    }

    override fun onRestart() {
        super.onRestart()
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


}