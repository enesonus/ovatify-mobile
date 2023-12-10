package com.sabanci.ovatify.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.VerticalMusicActivity
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.databinding.MusicParentItemBinding
import com.sabanci.ovatify.model.LibraryModel

class HomeAdapter2(private val songList : List<LibraryModel>, private val listener: IhomeclickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class FirstViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val welcome_user=itemView.findViewById<TextView>(R.id.welcome__user)
        val music1photo=itemView.findViewById<ImageView>(R.id.music1photo)
        val music1name=itemView.findViewById<TextView>(R.id.music1name)
        val music1artist=itemView.findViewById<TextView>(R.id.music1artist)
        val chartphoto=itemView.findViewById<ImageView>(R.id.chartphoto)
    }


    class CollectionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = MusicParentItemBinding.bind(itemView)
    }

    private val FIRST_ITEM=0
    private val OTHER_ITEMS=1

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
        return when (viewType) {
            FIRST_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.new_head_home, parent, false)
                FirstViewHolder(view)

            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.music_parent_item, parent, false)
                CollectionViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("List Size", "${songList.size}")
        return songList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FirstViewHolder -> {
                // Bind data for the first item
                // For instance: holder.bind(dataList[position])
                //will implement when backend data comes
                Log.d("first view holder", "first view holder works")
            }

            is CollectionViewHolder -> {

                // Bind data for other items
                // For instance: holder.bind(dataList[position])
                //will implement when backend data comes
                Log.d("collection view holder", "collection view holder works")

                Log.d("songlist size", songList[1].musicModels.size.toString())
                //songList[1].musicModels

                holder.binding.apply {
                    val collection = songList[position]
                    favoriteSongs.text =collection.title

                    favoriteSongs.setOnClickListener{
                        val intent = Intent(holder.itemView.context, VerticalMusicActivity::class.java)
                        intent.putExtra("List Title", collection.title)
                        holder.itemView.context.startActivity(intent)
                    }

                    val songAdapter = SongAdapter(collection.musicModels, object : IhomeclickListener {
                        override fun onItemClick(position: Int) {
                            // Handle item click here
                            Log.d("Item Position In Main Page Your Fvaorites", position.toString())
                            //Log.d("Item Position", "${songList[position]}")
                            val context = holder.itemView.context
                            val clickedItem = collection
                            val intent = Intent(context, ShowMusicActivity::class.java)
                            intent.putExtra("song", collection.musicModels[position].id)
                            context.startActivity(intent)
                            //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                        }
                    })
                    rvSongChild.adapter=songAdapter
                    rvSongChild.adapter?.notifyDataSetChanged()
                }

                holder.itemView.setOnClickListener{
                    listener.onItemClick(position)
                }

            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            FIRST_ITEM
        } else {
            OTHER_ITEMS
        }
    }

}