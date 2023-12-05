package com.sabanci.ovatify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sabanci.ovatify.R
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.Songs
import com.sabanci.ovatify.databinding.SongRowBinding

class VerticalSongListAdapter(private val songList : ArrayList<Songs>, private val listener: IhomeclickListener)
    : RecyclerView.Adapter<VerticalSongListAdapter.SongViewHolder>()
{

    private lateinit var binding: SongRowBinding
    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val songImage : ImageView = itemView.findViewById(R.id.songImage)
        val songName : TextView = itemView.findViewById(R.id.songName)
        val artistName : TextView = itemView.findViewById(R.id.artistName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_row, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {

        val song = songList[position]
        holder.songImage.load(song.img_url)
        holder.songName.text = song.name
        holder.artistName.text = song.main_artist
    }

}