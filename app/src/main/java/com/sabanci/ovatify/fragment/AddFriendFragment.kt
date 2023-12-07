package com.sabanci.ovatify.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sabanci.ovatify.R
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.RResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendFragment: Fragment(R.layout.add_friend) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText=view.findViewById<EditText>(R.id.friend_edittext)
        val sendButton=view.findViewById<RelativeLayout>(R.id.send_request_button)
        sendButton.setOnClickListener {
            Log.e("click","clicked")
            val call=RetrofitClient.apiService.sendFriendRequest(mapOf("username" to editText.text.toString()) )
            call.enqueue(object:Callback<RResponse>{
                override fun onResponse(call: Call<RResponse>, response: Response<RResponse>) {
                    Log.e("response code",response.code().toString()+response.body())
                    if(response.isSuccessful){
                        Toast.makeText(requireContext(),"Friendship Request is sent",Toast.LENGTH_SHORT).show()
                    }
                    else if(response.code()==400){
                        val error: RResponse? = response.errorBody()?.let {
                            Gson().fromJson(it.string(), RResponse::class.java)
                        }
                        if (error != null) {
                            Toast.makeText(requireContext(),error.error,Toast.LENGTH_SHORT).show()
                            Log.e("problem",response.errorBody().toString())
                        }
                    }
                    else if (response.code()==409)
                        Toast.makeText(requireContext(),"There is already a pending request coming from user",Toast.LENGTH_SHORT).show()
                    else {
                        Toast.makeText(requireContext(),response.body().toString(),Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RResponse>, t: Throwable) {
                    Toast.makeText(requireContext(),"Some error happened",Toast.LENGTH_SHORT).show()
                    Log.e("problem","problematic")
                }

            })
        }
    }
}