package com.sabanci.ovatify

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanci.ovatify.adapter.NotificationAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.GetSuggestionsReturn
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.SuggestionItem
import com.sabanci.ovatify.databinding.NotificationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity:AppCompatActivity() {
    lateinit var binding:NotificationBinding
    lateinit var datalist:ArrayList<SuggestionItem>
    lateinit var adapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=NotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener { onBackPressed() }
        datalist= arrayListOf()
        val layoutManager= LinearLayoutManager(this)
        binding.notifRecView.layoutManager=layoutManager
        adapter= NotificationAdapter(datalist,object :IhomeclickListener{
            override fun onItemClick(position: Int) {
                val intent= Intent(this@NotificationActivity,ShowMusicActivity::class.java)
                intent.putExtra("song",datalist[position].song_id)
                startActivity(intent)
            }

        })
        getData()
        binding.notifRecView.adapter=adapter


    }
    private fun getData(){
        binding.progressBar.visibility=View.VISIBLE
        val call=RetrofitClient.apiService.getSuggestions()
        call.enqueue(object:Callback<GetSuggestionsReturn>{
            override fun onResponse(
                call: Call<GetSuggestionsReturn>,
                response: Response<GetSuggestionsReturn>
            ) {
                if(response.isSuccessful){
                    val apireturn=response.body()
                    if (apireturn != null) {
                        if(apireturn.items.isNullOrEmpty()){
                            Toast.makeText(this@NotificationActivity,"No Notifications",Toast.LENGTH_SHORT).show()
                        }
                        binding.progressBar.visibility=View.INVISIBLE
                        binding.notifRecView.visibility=View.VISIBLE
                        datalist.addAll(apireturn.items)
                        adapter.notifyDataSetChanged()
                    }
                }
                else {
                    Log.e("api call returned",response.code().toString())
                }

            }

            override fun onFailure(call: Call<GetSuggestionsReturn>, t: Throwable) {
                Log.e("api call fail","failed")
            }

        })
    }
}