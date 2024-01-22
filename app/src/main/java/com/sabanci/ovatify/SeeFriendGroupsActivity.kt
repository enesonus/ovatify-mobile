package com.sabanci.ovatify

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.adapter.FriendGroupAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.FriendGroup
import com.sabanci.ovatify.data.GetFriendGroupsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.Playlistitem
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.databinding.SeeFriendGroupsBinding
import com.sabanci.ovatify.databinding.SeePlaylistsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendGroupsActivity:AppCompatActivity() {
    private lateinit var binding: SeeFriendGroupsBinding
    private lateinit var datalist:ArrayList<FriendGroup>
    private lateinit var adapter:FriendGroupAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=SeeFriendGroupsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            val intent= Intent(this,HomePageActivity::class.java)
            startActivity(intent)
        }
        binding.createAFriendGroup.setOnClickListener {
            createAFriendGroup()
        }
        datalist= arrayListOf()
        getFriendGroups()
        val layoutManager= LinearLayoutManager(this)
        binding.recviewfriendgroup.layoutManager=layoutManager
        binding.recviewfriendgroup.setHasFixedSize(true)

    }
    private fun getFriendGroups(){
        binding.prbarfriendgroup.visibility=View.VISIBLE
        binding.recviewfriendgroup.visibility=View.INVISIBLE
        val call =RetrofitClient.apiService.getFriendGroups()
        call.enqueue(object:Callback<GetFriendGroupsReturn>{
            override fun onResponse(
                call: Call<GetFriendGroupsReturn>,
                response: Response<GetFriendGroupsReturn>
            ) {
                if(response.isSuccessful){
                    val apireturn=response.body()
                    if (apireturn != null) {
                        datalist.addAll(apireturn.friend_groups)
                        binding.recviewfriendgroup.visibility=View.VISIBLE
                        binding.prbarfriendgroup.visibility=View.INVISIBLE
                        adapter= FriendGroupAdapter(datalist,object:IhomeclickListener{
                            override fun onItemClick(position: Int) {
                                val intent=Intent()
                            }
                        })
                        binding.recviewfriendgroup.adapter=adapter
                    }
                }
            }
            override fun onFailure(call: Call<GetFriendGroupsReturn>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun createAFriendGroup(){
        val call =RetrofitClient.apiService.createFriendGroup(mapOf("name" to "New Friend Group #X","description" to "This is a new friend group you created"))
        call.enqueue(object: Callback<RResponse> {
            override fun onResponse(call: Call<RResponse>, response: Response<RResponse>) {
                if(response.isSuccessful){
                    Toast.makeText(this@FriendGroupsActivity,"New Friend Group Created",Toast.LENGTH_SHORT).show()
                    getFriendGroups()
                }
                else {
                    Toast.makeText(this@FriendGroupsActivity,"could not create Friend Group ",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RResponse>, t: Throwable) {
                Toast.makeText(this@FriendGroupsActivity,"could not create Friend Group ",Toast.LENGTH_SHORT).show()
                Log.e("api call","call failed")
            }
        })
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val intent=Intent(this,HomePageActivity::class.java)
        startActivity(intent)
    }

}