package com.sabanci.ovatify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R

class HomeAdapter(private val songlist:ArrayList<Any>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class FirstViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val welcome_user=itemView.findViewById<TextView>(R.id.welcome__user)
        val music1photo=itemView.findViewById<ImageView>(R.id.music1photo)
        val music1name=itemView.findViewById<TextView>(R.id.music1name)
        val music1artist=itemView.findViewById<TextView>(R.id.music1artist)
        val chartphoto=itemView.findViewById<ImageView>(R.id.chartphoto)
    }
    class OtherViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val album1_photo=itemView.findViewById<ImageView>(R.id.album1_photo)
        val album1_name=itemView.findViewById<TextView>(R.id.album1_name)
        val album1_artist=itemView.findViewById<TextView>(R.id.album1_artist)
        val album2_photo=itemView.findViewById<ImageView>(R.id.album2_photo)
        val album2_name=itemView.findViewById<TextView>(R.id.album2_name)
        val album2_artist=itemView.findViewById<TextView>(R.id.album2_artist)
    }
    private val FIRST_ITEM=0
    private val OTHER_ITEMS=1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FIRST_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.head_home, parent, false)
                FirstViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.songs_home, parent, false)
                OtherViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FirstViewHolder -> {
                // Bind data for the first item
                // For instance: holder.bind(dataList[position])
                //will implement when backend data comes
            }
            is OtherViewHolder -> {
                // Bind data for other items
                // For instance: holder.bind(dataList[position])
                //will implement when backend data comes
            }
        }
    }

    override fun getItemCount(): Int {
        return songlist.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            FIRST_ITEM
        } else {
            OTHER_ITEMS
        }
    }


}