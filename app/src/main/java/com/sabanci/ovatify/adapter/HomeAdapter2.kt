package com.sabanci.ovatify.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.data.OneSong
import com.sabanci.ovatify.data.Song
import com.sabanci.ovatify.databinding.HorizontalSongsBinding

class HomeAdapter2(private val songList : List<Song>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    /*
    inner class SongItemViewHolder(private val binding : HorizontalSongsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindSongView(song: OneSong)
        {
            binding.song1Photo.setImageResource(song.image)
            binding.song1Artist.text = song.artist

        }
    }
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return   songList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}