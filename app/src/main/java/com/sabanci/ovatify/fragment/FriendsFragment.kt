package com.sabanci.ovatify.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.ShowMusicActivity
import com.sabanci.ovatify.adapter.FriendsAdapter
import com.sabanci.ovatify.adapter.HomeAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.AllFriendsReturn
import com.sabanci.ovatify.data.FriendReturn
import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.data.SpotifySearchReturn
import com.sabanci.ovatify.databinding.FriendsFragmentBinding
import com.sabanci.ovatify.databinding.ManualSongUploadFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendsFragment: Fragment() {
    private var _binding : FriendsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FriendsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Friends> //data is not initialized yet
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FriendsFragmentBinding.inflate(inflater, container, false)
        // Initialize and set up UI components
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist=ArrayList<Friends>()
        datainitialize()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.friendRecView)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= FriendsAdapter(datalist)
        recyclerView.adapter=adapter

    }
    private fun datainitialize(){
        binding.progressBar.visibility = View.VISIBLE
        val call=RetrofitClient.apiService.getAllFriends()
        call.enqueue(object: Callback<AllFriendsReturn>{
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
                        binding.progressBar.visibility=View.INVISIBLE
                        Log.e("dataset",datalist.toString())
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<AllFriendsReturn>, t: Throwable) {
                Toast.makeText(requireContext(),"Friend data did not come",Toast.LENGTH_SHORT).show()
            }

        })


    }
}