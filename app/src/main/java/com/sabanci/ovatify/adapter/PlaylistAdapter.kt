package com.sabanci.ovatify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sabanci.ovatify.R
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.Playlistitem

class PlaylistAdapter(val listOfPlaylist :ArrayList<Playlistitem>,val listener :IhomeclickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PlaylistViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name=itemView.findViewById<TextView>(R.id.playlistName)
        val photo1=itemView.findViewById<ImageView>(R.id.playlistPhoto1)
        val photo2=itemView.findViewById<ImageView>(R.id.playlistPhoto2)
        val photo3=itemView.findViewById<ImageView>(R.id.playlistPhoto3)
        val photo4=itemView.findViewById<ImageView>(R.id.playlistPhoto4)
        val description=itemView.findViewById<TextView>(R.id.playlistdescrp)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
         return PlaylistAdapter.PlaylistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfPlaylist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PlaylistViewHolder) {
            holder.itemView.setOnClickListener{
                listener.onItemClick(position)
            }
            val data = listOfPlaylist[position]
            holder.name.text = data.name
            holder.description.text = data.description
            if (data.song_imgs.isNotEmpty()) {
                if (data.song_imgs.size >=1) {
                    holder.photo1.load(data.song_imgs[0])
                    if (data.song_imgs.size >=2) {
                        holder.photo2.load(data.song_imgs[1])
                        if (data.song_imgs.size>=3) {
                            holder.photo3.load(data.song_imgs[2])
                            if (data.song_imgs.size>=4) {
                                holder.photo4.load(data.song_imgs[3])
                            } else {
                                holder.photo4.load(data.song_imgs[2])
                            }
                        } else {
                            holder.photo3.load(data.song_imgs[1])
                            holder.photo4.load(data.song_imgs[1])
                        }
                    } else {
                        holder.photo2.load(data.song_imgs[0])
                        holder.photo3.load(data.song_imgs[0])
                        holder.photo4.load(data.song_imgs[0])
                    }
                }
            }
        }
    }
}