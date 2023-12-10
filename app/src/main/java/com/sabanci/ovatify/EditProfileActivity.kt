package com.sabanci.ovatify

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.gson.Gson
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.data.UserInfo
import com.sabanci.ovatify.data.UserRequestBody
import com.sabanci.ovatify.data.UserStats
import com.sabanci.ovatify.databinding.EditProfileActivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.System.load

class EditProfileActivity:AppCompatActivity() {

    lateinit var binding: EditProfileActivityBinding
    var currentimg_url :String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener { onBackPressed() }
        val call1 =RetrofitClient.apiService.getUserProfile()
        call1.enqueue(object:Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if(response.isSuccessful){
                    val userInfo=response.body()
                    if (userInfo != null) {
                        binding.userphoto.load(userInfo.img_url)
                    }
                    else {
                        binding.userphoto.setImageResource(R.drawable.profile_logo)
                    }
                    binding.username.setText(userInfo!!.name)
                    binding.checkBoxDataSharing.isChecked=userInfo.preferences.data_sharing
                    binding.checkBoxDataProcessing.isChecked=userInfo.preferences.data_processing

                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("api call","failed")
            }
        })
        val call2=RetrofitClient.apiService.getUsersStat()
        call2.enqueue(object :Callback<UserStats>{
            override fun onResponse(call: Call<UserStats>, response: Response<UserStats>) {
                if (response.isSuccessful){
                    val userStat=response.body()
                    binding.friendCount.setText("Friends: "+userStat!!.friend_count.toString())
                    binding.ratedCount.setText("Rated Songs: "+userStat!!.rated_count.toString())
                    binding.ratingAverage.setText("Average Raing: "+userStat!!.rating_average.toString())
                    binding.music2.visibility=View.VISIBLE
                    binding.progressBar4.visibility=View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<UserStats>, t: Throwable) {
                Log.e("api call","problem2")            }
        })

        binding.EditButton.setOnClickListener {
            Toast.makeText(this,"Click an Item to edit",Toast.LENGTH_SHORT).show()
            binding.EditButton.visibility= View.INVISIBLE
            binding.saveButtonNew.visibility= View.VISIBLE
            binding.cancelButtonNew.visibility= View.VISIBLE
            binding.checkBoxDataProcessing.isClickable=true
            binding.checkBoxDataSharing.isClickable=true
            binding.userphoto.isClickable=true
            binding.username.isEnabled=true
        }
        binding.saveButtonNew.setOnClickListener {
            val userRequestBody=UserRequestBody(
                username = binding.username.text.toString(),
                email = null,
                img_url = currentimg_url,
                data_processing_consent = binding.checkBoxDataProcessing.isChecked,
                data_sharing_consent = binding.checkBoxDataSharing.isChecked)
            val callforedit=RetrofitClient.apiService.editUserPreferences(userRequestBody)
            callforedit.enqueue(object:Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        runOnUiThread {
                            Toast.makeText(this@EditProfileActivity, "Updated Successfully", Toast.LENGTH_SHORT).show()
                        }
                        binding.EditButton.visibility= View.VISIBLE
                        binding.saveButtonNew.visibility= View.INVISIBLE
                        binding.cancelButtonNew.visibility= View.INVISIBLE
                        binding.userphoto.isClickable=false
                        binding.checkBoxDataProcessing.isClickable=false
                        binding.checkBoxDataSharing.isClickable=false
                        binding.username.isEnabled=false
                    }
                    else if (response.code()==400) {
                        val error: RResponse? = response.errorBody()?.let {
                            Gson().fromJson(it.string(), RResponse::class.java)
                        }
                        runOnUiThread {
                            if (error != null) {
                                Toast.makeText(this@EditProfileActivity, error.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("api call","problem with saving")
                }
            })


        }
        binding.cancelButtonNew.setOnClickListener {
            binding.EditButton.visibility= View.VISIBLE
            binding.saveButtonNew.visibility= View.INVISIBLE
            binding.cancelButtonNew.visibility= View.INVISIBLE
            binding.userphoto.isClickable=false
            binding.username.isEnabled=false
            val callforbefore =RetrofitClient.apiService.getUserProfile()
            callforbefore.enqueue(object:Callback<UserInfo>{
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    if(response.isSuccessful){
                        val userInfo=response.body()
                        if (userInfo != null) {
                            binding.userphoto.load(userInfo.img_url)
                        }
                        else {
                            binding.userphoto.setImageResource(R.drawable.profile_logo)
                        }
                        binding.username.setText(userInfo!!.name)
                        binding.checkBoxDataSharing.isChecked=userInfo.preferences.data_sharing
                        binding.checkBoxDataProcessing.isChecked=userInfo.preferences.data_processing

                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    Log.e("api call","failed")
                }
            })

        }
        binding.userphoto.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.setForceShowIcon(true);
            popupMenu.menuInflater.inflate(R.menu.image_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.image1 -> {
                        currentimg_url =
                            "https://cdn.betterttv.net/emote/5aed4eaef12e8525a0da50b0/3x.webp"
                        binding.userphoto.load(currentimg_url)
                    }
                    R.id.image2 -> {
                        currentimg_url = "https://cdn.betterttv.net/emote/61f1547a06fd6a9f5be23974/3x.webp"
                        binding.userphoto.load(currentimg_url)
                    }
                    R.id.image3 -> {
                        currentimg_url =
                            "https://cdn.betterttv.net/emote/60b07615f8b3f62601c34741/3x.webp"
                        binding.userphoto.load(currentimg_url)
                    }
                    R.id.image4 -> {
                        currentimg_url =
                            "https://cdn.betterttv.net/emote/5694fa70720c7d4179f14d50/3x.webp"
                        binding.userphoto.load(currentimg_url)

                    }
                }
                true
            }

            popupMenu.show()
        }
    }
        }

