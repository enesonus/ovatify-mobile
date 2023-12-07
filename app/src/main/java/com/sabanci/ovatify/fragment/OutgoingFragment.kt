package com.sabanci.ovatify.fragment

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
import com.sabanci.ovatify.adapter.FriendsAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.FriendReturn
import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.databinding.FriendsFragmentBinding
import com.sabanci.ovatify.databinding.OutgoingFragmentBinding
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutgoingFragment(): Fragment() {
    private var _binding : OutgoingFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FriendsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Friends> //data is not initialized yet
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OutgoingFragmentBinding.inflate(inflater, container, false)
        // Initialize and set up UI components
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist=ArrayList()
        datainitialize()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.outgoingRecView)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= FriendsAdapter(datalist)
        recyclerView.adapter=adapter

    }
    private fun datainitialize(){
        binding.progressBar3.visibility=View.VISIBLE
        Log.e("func","calisiyo")
        val call= RetrofitClient.apiService.getOutgoingRequests()
        call.enqueue(object: Callback<FriendReturn> {
            override fun onResponse(
                call: Call<FriendReturn>,
                response: Response<FriendReturn>
            ) {
                Log.e("response",response.code().toString())
                if(response.isSuccessful){
                    val requests:FriendReturn = response.body()!!
                    if(datalist!=null&&requests.requests!=null){
                        datalist.clear()
                        datalist.addAll(requests.requests)
                        binding.progressBar3.visibility=View.INVISIBLE
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<FriendReturn>, t: Throwable) {
                Toast.makeText(requireContext(),"Friend data did not come", Toast.LENGTH_SHORT).show()
            }

        })


    }
}
