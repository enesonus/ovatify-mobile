package com.sabanci.ovatify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.databinding.MusicParentItemBinding

class LibraryAdapter2 (private val collection: List<LibraryModel>) : RecyclerView.Adapter<LibraryAdapter2.CollectionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_parent_item,parent,false)
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.binding.apply {
            val collection = collection[position]
            favoriteSongs.text =collection.title
            val songAdapter = SongAdapter(collection.musicModels)
            rvSongChild.adapter=songAdapter
        }
    }

    override fun getItemCount() =collection.size
    inner class CollectionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = MusicParentItemBinding.bind(itemView)
    }
}