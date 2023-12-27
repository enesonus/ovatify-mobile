package com.sabanci.ovatify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowPlaylistActivity
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.data.Songs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongPlaylistAdapter(val listOfSongs :ArrayList<Songs>,val playlist_id:String, val listener : IhomeclickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class SongViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name=itemView.findViewById<TextView>(R.id.song_name)
        val artist=itemView.findViewById<TextView>(R.id.song_artist)
        val image=itemView.findViewById<ImageView>(R.id.song_image)
        val remove=itemView.findViewById<Button>(R.id.button_remove)
        var isRemoved=false
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_song_item, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfSongs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is SongViewHolder){
            holder.itemView.setOnClickListener{
                listener.onItemClick(position)
            }
            holder.remove.setOnClickListener {
                if (!holder.isRemoved) {
                    val call = RetrofitClient.apiService.removeSongFromPlaylist(
                        playlist_id,
                        listOfSongs[position].id
                    )
                    call.enqueue(object : Callback<RResponse> {
                        override fun onResponse(
                            call: Call<RResponse>,
                            response: Response<RResponse>
                        ) {
                            Toast.makeText(holder.itemView.context,"Song is removed succesfully",Toast.LENGTH_SHORT).show()
                            holder.remove.text="Add"
                            holder.isRemoved=true

                        }

                        override fun onFailure(call: Call<RResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
                }
                else {
                    val call =RetrofitClient.apiService.addSongToPlaylist(mapOf("playlist_id" to playlist_id,"song_id" to listOfSongs[position].id))
                    call.enqueue(object:Callback<RResponse>{
                        override fun onResponse(
                            call: Call<RResponse>,
                            response: Response<RResponse>
                        ) {
                            Toast.makeText(holder.itemView.context,"Song is added succesfully",Toast.LENGTH_SHORT).show()
                            holder.remove.text="Remove"
                            holder.isRemoved=false
                        }
                        override fun onFailure(call: Call<RResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }
            val data=listOfSongs[position]
            holder.name.text=data.name
            holder.artist.text=data.main_artist
            holder.image.load(data.img_url)
        }
    }

}
