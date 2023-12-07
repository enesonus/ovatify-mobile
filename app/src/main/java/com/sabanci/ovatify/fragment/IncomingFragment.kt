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
import com.sabanci.ovatify.adapter.IncomingAdapter
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.FriendReturn
import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.databinding.FriendsFragmentBinding
import com.sabanci.ovatify.databinding.IncomingFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncomingFragment : Fragment() {
    private var _binding : IncomingFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: IncomingAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Friends> //data is not initialized yet
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = IncomingFragmentBinding.inflate(inflater, container, false)
        // Initialize and set up UI components
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist=ArrayList<Friends>()
        datainitialize()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.incomingRecView)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= IncomingAdapter(datalist)
        recyclerView.adapter=adapter

    }
    private fun datainitialize(){
        binding.progressBar2.visibility=View.VISIBLE
        val call= RetrofitClient.apiService.getIncomingRequests()
        call.enqueue(object: Callback<FriendReturn> {
            override fun onResponse(
                call: Call<FriendReturn>,
                response: Response<FriendReturn>
            ) {
                if(response.isSuccessful){
                    val
                    requests:FriendReturn = response.body()!!
                    if(datalist!=null&&requests.requests!=null){
                        datalist.clear()
                        datalist.addAll(requests.requests)
                        binding.progressBar2.visibility=View.INVISIBLE
                        Log.e("y",datalist.toString())
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<FriendReturn>, t: Throwable) {
                Toast.makeText(requireContext(),"Friend data did not come", Toast.LENGTH_SHORT)
            }

        })


    }
}
