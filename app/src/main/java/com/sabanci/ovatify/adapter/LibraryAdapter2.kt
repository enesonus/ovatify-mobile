package com.sabanci.ovatify.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.databinding.MusicParentItemBinding

class LibraryAdapter2 (private val collection: List<LibraryModel>, private val listener: IhomeclickListener) : RecyclerView.Adapter<LibraryAdapter2.CollectionViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_parent_item,parent,false)
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.binding.apply {
            val collectionPosition = collection[position]
            favoriteSongs.text =collectionPosition.title
            val songAdapter = SongAdapter(collectionPosition.musicModels, object :
                IhomeclickListener {
                override fun onItemClick(position: Int) {
                    // Handle item click here
                    Log.d("Item Position", position.toString())

                    val context = holder.itemView.context
                    val clickedItem = collectionPosition
                    val intent = Intent(context, ShowMusicActivity::class.java)
                    intent.putExtra("song", collectionPosition.musicModels[position].id)
                    context.startActivity(intent)


                    //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                }
            })
            rvSongChild.adapter=songAdapter
        }

        holder.itemView.setOnClickListener{
            listener.onItemClick(position)
        }


    }

    override fun getItemCount() =collection.size
    inner class CollectionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = MusicParentItemBinding.bind(itemView)
    }
}