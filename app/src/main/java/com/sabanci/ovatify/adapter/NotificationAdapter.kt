package com.sabanci.ovatify.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.yml.charts.common.extensions.isNotNull
import coil.load
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.SuggestionItem

class NotificationAdapter(val datalist:ArrayList<SuggestionItem>,val listener: IhomeclickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class SuggestionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendImage = itemView.findViewById<ImageView>(R.id.FriendImage)
        val friendName = itemView.findViewById<TextView>(R.id.FriendName)
        val songName=itemView.findViewById<TextView>(R.id.SongName)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return SuggestionItemViewHolder(view)
    }

    override fun getItemCount(): Int {
       return datalist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is SuggestionItemViewHolder){
            val data=datalist[position]
            if (data.suggester_img_url.isNotNull()){
                holder.friendImage.load(data.suggester_img_url)
            }
            else{
                holder.friendImage.setImageResource(R.drawable.ellipse_457_shape)
            }
            holder.friendName.text=data.suggester_name
            holder.songName.text="Suggested you the song \""+data.song_name+"\" click to see the song"
            holder.itemView.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }

}