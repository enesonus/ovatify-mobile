package com.sabanci.ovatify.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.gson.Gson
import com.sabanci.ovatify.R
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.RResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuggestAdapter (val datalist :ArrayList<Friends>,val listener: IhomeclickListener) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var selecteditem=-1
    class FriendItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendImage = itemView.findViewById<ImageView>(R.id.FriendImage)
        val friendName = itemView.findViewById<TextView>(R.id.FriendName)
        val prnt=itemView.findViewById<ConstraintLayout>(R.id.prnt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.suggest_item, parent, false)
        return FriendItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val friend = datalist[position]
        if (holder is FriendItemViewHolder) {
            holder.friendImage.load(friend.img_url)
            holder.friendName.text = friend.name
            holder.prnt.setBackgroundResource(R.drawable.friend_bacground)
            holder.itemView.setOnClickListener {
                holder.prnt.setBackgroundResource(R.drawable.friend_selected)
                listener.onItemClick(position)
                handleItemClick(position)
            }
    }
    }

    fun handleItemClick(position: Int) {
        val previousSelectedItem = selecteditem
        selecteditem = position

        if(previousSelectedItem!=-1){
            notifyItemChanged(previousSelectedItem)
        }
    }

}

