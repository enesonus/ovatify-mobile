package com.sabanci.ovatify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.databinding.MusicItemTryBinding
import com.sabanci.ovatify.model.MusicModel
import coil.load
import com.sabanci.ovatify.data.IhomeclickListener

class SongAdapter (private val musicModelList : List<MusicModel>, private val listener: IhomeclickListener) : RecyclerView.Adapter<SongAdapter.SongViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item_try,parent,false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {

        holder.itemView.setOnClickListener {listener.onItemClick(position)}

        holder.binding.apply {
            imageMusic.load(musicModelList[position].imageUrl)
            textMusicName.text = musicModelList[position].songName
            textArtistName.text = musicModelList[position].artistName
        }
    }

    override fun getItemCount() =musicModelList.size


    inner class SongViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = MusicItemTryBinding.bind(itemView)
    }

}