package com.sabanci.ovatify.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sabanci.ovatify.R
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.data.IhomeclickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendsAdapter(val friendList:ArrayList<Friends> ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        Log.e("FriendsAdapter", "Initial dataset size: ${friendList.size}")
    }
    class FriendItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val friendImage=itemView.findViewById<ImageView>(R.id.FriendImage)
        val friendName=itemView.findViewById<TextView>(R.id.FriendName)
        //val friendUserName=itemView.findViewById<TextView>(R.id.FriendUserName)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return FriendItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //bind data here
        val friend=friendList[position]
        if (holder is FriendItemViewHolder){
            holder.friendImage.load(friend.img_url)
            holder.friendName.text=friend.name
        }
        //

    }
}