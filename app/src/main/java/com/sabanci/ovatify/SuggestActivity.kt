package com.sabanci.ovatify

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanci.ovatify.adapter.SuggestAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.AllFriendsReturn
import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.databinding.ProfilePageBinding
import com.sabanci.ovatify.databinding.SuggestBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SuggestActivity:AppCompatActivity() {
    private lateinit var binding: SuggestBinding
    private lateinit var datalist:ArrayList<Friends>
    var selectedPos=0
    lateinit var adapter:SuggestAdapter
    lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= SuggestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id= intent.getStringExtra("id").toString()

        binding.backButton.setOnClickListener{
            onBackPressed()
        }
        datalist= arrayListOf()
        getData()
        val layoutManager= LinearLayoutManager(this)
        binding.suggestRecView.layoutManager=layoutManager
        binding.suggestRecView.setHasFixedSize(true)
        adapter= SuggestAdapter(datalist,object:IhomeclickListener{
            override fun onItemClick(position: Int) {
                selectedPos=position
            }

        })
        binding.suggestRecView.adapter=adapter
        binding.suggestButton.setOnClickListener {
            binding.suggestText.text="Suggesting..."
            val call= RetrofitClient.apiService.suggestSong(mapOf("receiver_user" to datalist[selectedPos].name,"song_id" to id))
            call.enqueue(object:retrofit2.Callback<RResponse>{
                override fun onResponse(call: Call<RResponse>, response: Response<RResponse>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@SuggestActivity,"Song is suggested succesfully",Toast.LENGTH_SHORT).show()
                    }

                    else {
                        Toast.makeText(this@SuggestActivity,"Can't suggest",Toast.LENGTH_SHORT).show()
                    }
                    binding.suggestText.text="Suggest!"
                }

                override fun onFailure(call: Call<RResponse>, t: Throwable) {
                    binding.suggestText.text="Suggest!"
                    Toast.makeText(this@SuggestActivity,"Can't suggest",Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
    private fun getData(){
        binding.progressBar.visibility=View.VISIBLE
        val call=RetrofitClient.apiService.getAllFriends()
        call.enqueue(object: retrofit2.Callback<AllFriendsReturn> {
            override fun onResponse(
                call: Call<AllFriendsReturn>,
                response: Response<AllFriendsReturn>
            ) {
                if(response.isSuccessful){
                    Log.e("response",response.code().toString())
                    val requests:AllFriendsReturn = response.body()!!
                    if(datalist!=null&&requests.friends!=null){
                        datalist.clear()
                        datalist.addAll(requests.friends)
                        binding.progressBar.visibility= View.INVISIBLE
                        binding.suggestRecView.visibility=View.VISIBLE
                        Log.e("dataset",datalist.toString())
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<AllFriendsReturn>, t: Throwable) {
                Toast.makeText(this@SuggestActivity,"Friend data did not come", Toast.LENGTH_SHORT).show()
            }

        })
    }

}