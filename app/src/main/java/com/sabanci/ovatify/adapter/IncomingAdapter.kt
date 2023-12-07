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
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.gson.Gson
import com.sabanci.ovatify.FriendFlowActivity
import com.sabanci.ovatify.R
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.data.RResponse
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncomingAdapter(var datalist :ArrayList<Friends>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    class IncomingItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val friendImage=itemView.findViewById<ImageView>(R.id.FriendImageincoming)
        val friendName=itemView.findViewById<TextView>(R.id.FriendNameincoming)
        val acceptbutton=itemView.findViewById<Button>(R.id.acceptButton)
        val rejectbutton=itemView.findViewById<Button>(R.id.rejectButton)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.incoming_item, parent, false)
        return IncomingItemViewHolder(view)
    }

    override fun getItemCount(): Int {
            return datalist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val friend=datalist[position]
        if (holder is IncomingItemViewHolder){
            Log.e("holder","incomingviewholder")
            holder.friendImage.load(friend.img_url)
            holder.friendName.text=friend.name
            holder.acceptbutton.setOnClickListener{
                Log.e("accept","clicked")
                val callforaccept=RetrofitClient.apiService.acceptFriendRequest(mapOf("username" to friend.name))
                callforaccept.enqueue(object :Callback<RResponse>{
                    override fun onResponse(call: Call<RResponse>, response: Response<RResponse>) {
                        Log.e("reponse","Accept"+response.code().toString())
                        if (response.isSuccessful) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Accepted Succesfuly",
                                Toast.LENGTH_SHORT
                            ).show()
                            if(holder.adapterPosition!=RecyclerView.NO_POSITION){
                                removeItem(holder.adapterPosition)
                            }
                        }
                        if (response.code() == 400) {

                            val error: RResponse? = response.errorBody()?.let {
                                Gson().fromJson(it.string(), RResponse::class.java)
                            }
                            if (error != null) {
                                Toast.makeText(holder.itemView.context,error.error,Toast.LENGTH_SHORT).show()
                                Log.e("problem",response.errorBody().toString())
                        }
                    }
                    }

                    override fun onFailure(call: Call<RResponse>, t: Throwable) {
                        Toast.makeText(holder.itemView.context,"Something goes wrong",Toast.LENGTH_SHORT).show()
                    }

                })
            }
            holder.rejectbutton.setOnClickListener{
                Log.e("reject","clicked")
                val callforreject=RetrofitClient.apiService.rejectFriendRequest(mapOf("username" to friend.name))
                callforreject.enqueue(object :Callback<RResponse>{
                    override fun onResponse(call: Call<RResponse>, response: Response<RResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Rejected Succesfuly",
                                Toast.LENGTH_SHORT
                            ).show()
                            if(holder.adapterPosition!=RecyclerView.NO_POSITION){
                                removeItem(holder.adapterPosition)
                            }
                        }
                        if (response.code() == 400) {
                            val error: RResponse? = response.errorBody()?.let {
                                Gson().fromJson(it.string(), RResponse::class.java)
                            }
                            if (error != null) {
                                Toast.makeText(holder.itemView.context,error.error,Toast.LENGTH_SHORT).show()
                                Log.e("problem",response.errorBody().toString())
                            }
                        }
                    }

                    override fun onFailure(call: Call<RResponse>, t: Throwable) {
                        Toast.makeText(holder.itemView.context,"Something goes wrong",Toast.LENGTH_SHORT).show()
                    }

                })
             }
        }
    }
    fun removeItem(position: Int) {
        datalist?.removeAt(position)
        notifyItemRemoved(position)
    }

}