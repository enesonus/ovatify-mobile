package com.sabanci.ovatify.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.OneSong
import com.sabanci.ovatify.data.Song
import com.sabanci.ovatify.databinding.HorizontalSongsBinding
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
                val view = LayoutInflater.from(parent.context).inflate(R.layout.head_home, parent, false)
                HomeAdapter2.FirstViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.explore_music_list_fragment, parent, false)
                HomeAdapter2.CollectionViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeAdapter2.FirstViewHolder -> {
                // Bind data for the first item
                // For instance: holder.bind(dataList[position])
                //will implement when backend data comes
            }
            is HomeAdapter2.CollectionViewHolder -> {

                // Bind data for other items
                // For instance: holder.bind(dataList[position])
                //will implement when backend data comes

                holder.binding.apply {
                    val collection = songList[position]
                    favoriteSongs.text =collection.title
                    val songAdapter = SongAdapter(collection.musicModels, object : IhomeclickListener {
                        override fun onItemClick(position: Int) {
                            // Handle item click here
                            Log.d("Item Position", "${songList[position]}")
                            /*
                            val clickedItem = songList[position]
                            val intent = Intent(, ShowMusicActivity::class.java)
                            intent.putExtra("song", songList[position].id)
                            startActivity(intent)

                             */
                            //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                        }
                    })
                    rvSongChild.adapter=songAdapter
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