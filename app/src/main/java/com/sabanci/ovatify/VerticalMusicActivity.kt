package com.sabanci.ovatify

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sabanci.ovatify.adapter.VerticalSongListAdapter
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.databinding.VerticalSongsListBinding

class VerticalMusicActivity:AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var songList: MutableList<Songs>
    private lateinit var verticalSongsListAdapter: VerticalSongListAdapter
    private lateinit var binding: VerticalSongsListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vertical_songs_list)

        recyclerView = findViewById(R.id.verticalSongsRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)


        var song1 = Songs(
            "1",
            "Music 1",
            "2005",
            "Artist 1",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTspGEmlML92qIKTrL5q3GO2gf_TKXvojEHKS6QQo5Tbt4VUkGZGYnh8pwRGTvxD8ioTqE&usqp=CAU"
        )
        var song2 = Songs(
            "1",
            "Music 1",
            "2005",
            "Artist 1",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTspGEmlML92qIKTrL5q3GO2gf_TKXvojEHKS6QQo5Tbt4VUkGZGYnh8pwRGTvxD8ioTqE&usqp=CAU"
        )
        var song3 = Songs(
            "1",
            "Music 1",
            "2005",
            "Artist 1",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTspGEmlML92qIKTrL5q3GO2gf_TKXvojEHKS6QQo5Tbt4VUkGZGYnh8pwRGTvxD8ioTqE&usqp=CAU"
        )

        songList = mutableListOf(song1, song2, song3)

        verticalSongsListAdapter = VerticalSongListAdapter(songList)
        recyclerView.adapter = verticalSongsListAdapter




    }
}