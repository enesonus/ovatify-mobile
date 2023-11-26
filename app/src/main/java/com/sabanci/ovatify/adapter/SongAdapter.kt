package com.sabanci.ovatify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.databinding.MusicItemTryBinding
import com.sabanci.ovatify.model.MusicModel
import coil.load

class SongAdapter (private val movieModel: List<MusicModel>) : RecyclerView.Adapter<SongAdapter.SongViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item_try,parent,false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.binding.apply {
            imageMusic.load(movieModel[position].imageUrl)
        }
    }

    override fun getItemCount() =movieModel.size


    inner class SongViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = MusicItemTryBinding.bind(itemView)

    }

}